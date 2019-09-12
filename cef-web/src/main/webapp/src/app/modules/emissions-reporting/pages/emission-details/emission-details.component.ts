import { Component, OnInit, Input } from '@angular/core';
import { Emission } from 'src/app/shared/models/emission';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { Process } from 'src/app/shared/models/process';
import { Validators, FormBuilder, ValidatorFn, FormGroup } from '@angular/forms';
import { numberValidator } from 'src/app/modules/shared/directives/number-validator.directive';
import { CalculationMethodCode } from 'src/app/shared/models/calculation-method-code';
import { Pollutant } from 'src/app/shared/models/pollutant';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';
import { EmissionService } from 'src/app/core/services/emission.service';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { ReportingPeriodService } from 'src/app/core/services/reporting-period.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';

@Component({
  selector: 'app-emission-details',
  templateUrl: './emission-details.component.html',
  styleUrls: ['./emission-details.component.scss']
})
export class EmissionDetailsComponent implements OnInit {
  @Input() emission: Emission;
  @Input() reportingPeriod: ReportingPeriod;
  @Input() process: Process;
  @Input() editable = false;
  @Input() createMode = false;
  totalManualEntry = false;
  efNumeratorMismatch = false;
  efDenominatorMismatch = false;
  calculatedEf: number;

  processUrl: string;

  emissionForm = this.fb.group({
    pollutant: [null, Validators.required],
    emissionsFactor: ['', [Validators.required, numberValidator()]],
    emissionsFactorText: ['', [Validators.required, Validators.maxLength(100)]],
    emissionsNumeratorUom: [null, Validators.required],
    emissionsDenominatorUom: [null, Validators.required],
    emissionsCalcMethodCode: ['', Validators.required],
    totalEmissions: ['', [Validators.required, numberValidator()]],
    emissionsUomCode: [null, Validators.required],
    comments: ['', Validators.maxLength(200)],
  }, { validators: this.emissionsCalculatedValidator() });

  methodValues: CalculationMethodCode[];
  pollutantValues: Pollutant[];
  uomValues: UnitMeasureCode[];
  numeratorUomValues: UnitMeasureCode[];
  denominatorUomValues: UnitMeasureCode[];

  constructor(
    private emissionService: EmissionService,
    private periodService: ReportingPeriodService,
    private processService: EmissionsProcessService,
    private lookupService: LookupService,
    private route: ActivatedRoute,
    private router: Router,
    public formUtils: FormUtilsService,
    private fb: FormBuilder) { }

  ngOnInit() {

    this.lookupService.retrieveCalcMethod()
    .subscribe(result => {
      this.methodValues = result;
    });

    this.lookupService.retrievePollutant()
    .subscribe(result => {
      this.pollutantValues = result;
    });

    this.lookupService.retrieveUom()
    .subscribe(result => {
      this.uomValues = result;
      this.numeratorUomValues = this.uomValues.filter(val => val.efNumerator);
      this.denominatorUomValues = this.uomValues.filter(val => val.efDenominator);
    });

    this.route.data
    .subscribe(data => {
      this.createMode = data.create === 'true';
    });

    this.route.paramMap
    .subscribe(params => {

      if (!this.createMode) {
        this.emissionService.retrieve(+params.get('emissionId'))
        .subscribe(result => {
          this.emission = result;

          this.emissionForm.disable();
          this.emissionForm.reset(this.emission);
          this.calculatedEf = this.emission.emissionsFactor;
        });
      } else {
        this.emissionForm.enable();
      }

      this.periodService.retrieve(+params.get('periodId'))
      .subscribe(result => {
        this.reportingPeriod = result;

        this.processService.retrieve(this.reportingPeriod.emissionsProcessId)
        .subscribe(process => {
          this.process = process;

          this.processUrl = `/facility/${params.get('facilityId')}/report/${params.get('reportId')}/${BaseReportUrl.EMISSIONS_PROCESS}/${this.process.id}`;
        });
      });
    });

    this.emissionForm.controls.emissionsCalcMethodCode.valueChanges
    .subscribe(value => {
      this.onMethodChange(value, this.emissionForm.controls.emissionsCalcMethodCode.status);
    });

    this.emissionForm.controls.emissionsCalcMethodCode.statusChanges
    .subscribe(status => {
      this.onMethodChange(this.emissionForm.controls.emissionsCalcMethodCode.value, status);
    });
  }

  private onMethodChange(value: CalculationMethodCode, status: string) {

    if ('DISABLED' !== status) {
      if (value && value.totalDirectEntry) {
        this.emissionForm.get('emissionsFactor').disable();
        this.emissionForm.get('emissionsFactorText').disable();
        this.emissionForm.get('emissionsNumeratorUom').disable();
        this.emissionForm.get('emissionsDenominatorUom').disable();
        this.emissionForm.get('emissionsFactor').reset();
        this.emissionForm.get('emissionsFactorText').reset();
        this.emissionForm.get('emissionsNumeratorUom').reset();
        this.emissionForm.get('emissionsDenominatorUom').reset();
        this.totalManualEntry = true;
      } else {
        this.emissionForm.get('emissionsFactor').enable();
        this.emissionForm.get('emissionsFactorText').enable();
        this.emissionForm.get('emissionsNumeratorUom').enable();
        this.emissionForm.get('emissionsDenominatorUom').enable();
        this.totalManualEntry = false;
      }
    }
  }

  onCalculate() {
    if (!(this.reportingPeriod.calculationParameterUom && this.emissionForm.get('emissionsDenominatorUom').value
          && this.reportingPeriod.calculationParameterUom.code === this.emissionForm.get('emissionsDenominatorUom').value.code)) {
      this.efDenominatorMismatch = true;
    } else {
      this.efDenominatorMismatch = false;
    }

    if (!(this.emissionForm.get('emissionsUomCode').value && this.emissionForm.get('emissionsNumeratorUom').value
          && this.emissionForm.get('emissionsUomCode').value.code === this.emissionForm.get('emissionsNumeratorUom').value.code)) {
      this.efNumeratorMismatch = true;
    } else {
      this.efNumeratorMismatch = false;
    }

    if (!(this.efNumeratorMismatch || this.efDenominatorMismatch) && this.reportingPeriod.calculationParameterValue
          && this.emissionForm.get('emissionsFactor').valid) {
      const calculatedValue = this.reportingPeriod.calculationParameterValue * +this.emissionForm.get('emissionsFactor').value;
      if (calculatedValue) {
        this.calculatedEf = this.emissionForm.get('emissionsFactor').value;
        this.emissionForm.get('totalEmissions').setValue(calculatedValue);
      }
    }
  }

  onCancelEdit() {
    this.emissionForm.enable();
    if (!this.createMode) {
      this.emissionForm.reset(this.emission);
    }
  }

  onEdit() {
    this.emissionForm.enable();
  }

  onSubmit() {

    if (!this.emissionForm.valid) {
      this.emissionForm.markAllAsTouched();
    } else {

      const saveEmission = new Emission();
      Object.assign(saveEmission, this.emissionForm.value);

      if (this.createMode) {

        saveEmission.reportingPeriodId = this.reportingPeriod.id;

        this.emissionService.create(saveEmission)
        .subscribe(result => {

          this.router.navigate([this.processUrl]);
        });
      } else {

        saveEmission.id = this.emission.id;

        this.emissionService.update(saveEmission)
        .subscribe(result => {

          this.router.navigate([this.processUrl]);
        });
      }
    }
  }

  searchPollutants = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => this.pollutantValues.filter(v => v.pollutantName.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || v.pollutantCode.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || (v.pollutantCasId ? v.pollutantCasId.toLowerCase().indexOf(term.toLowerCase()) > -1 : false))
                                        .slice(0, 20))
    )

  pollutantFormatter = (result: Pollutant) => `${result.pollutantName}  -  ${result.pollutantCode} ${result.pollutantCasId ? ' - ' + result.pollutantCasId : ''}`;

  emissionsCalculatedValidator(): ValidatorFn {
    return (control: FormGroup): {[key: string]: any} | null => {
      const efControl = control.get('emissionsFactor');
      if (efControl.enabled) {
        return this.calculatedEf === efControl.value ? null : {emissionsCalculated: {value: this.calculatedEf}};
      }

      return null;
    };
  }

}
