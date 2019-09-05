import { Component, OnInit, Input } from '@angular/core';
import { Emission } from 'src/app/shared/models/emission';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { Process } from 'src/app/shared/models/process';
import { Validators, FormBuilder } from '@angular/forms';
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
  });

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
      if (value && value.totalDirectEntry) {
        this.emissionForm.get('emissionsFactor').disable();
        this.emissionForm.get('emissionsFactorText').disable();
        this.emissionForm.get('emissionsNumeratorUom').disable();
        this.emissionForm.get('emissionsDenominatorUom').disable();
        this.emissionForm.get('emissionsFactor').reset();
        this.emissionForm.get('emissionsFactorText').reset();
        this.emissionForm.get('emissionsNumeratorUom').reset();
        this.emissionForm.get('emissionsDenominatorUom').reset();
      } else {
        this.emissionForm.get('emissionsFactor').enable();
        this.emissionForm.get('emissionsFactorText').enable();
        this.emissionForm.get('emissionsNumeratorUom').enable();
        this.emissionForm.get('emissionsDenominatorUom').enable();
      }
    });
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

}
