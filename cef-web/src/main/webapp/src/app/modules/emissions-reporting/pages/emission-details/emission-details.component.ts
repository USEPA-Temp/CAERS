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
import { EmissionFactor } from 'src/app/shared/models/emission-factor';
import { EmissionFactorService } from 'src/app/core/services/emission-factor.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EmissionFactorModalComponent } from 'src/app/modules/emissions-reporting/components/emission-factor-modal/emission-factor-modal.component';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { SharedService } from 'src/app/core/services/shared.service';
import { ToastrService } from 'ngx-toastr';

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
  epaEmissionFactor = false;
  efNumeratorMismatch = false;
  efDenominatorMismatch = false;
  calculatedEf: number;

  readOnlyMode = true;

  processUrl: string;

  emissionForm = this.fb.group({
    pollutant: [null, Validators.required],
    formulaIndicator: [false, Validators.required],
    emissionsFactor: ['', [Validators.required, numberValidator()]],
    emissionsFactorFormula: [''],
    emissionsFactorText: ['', [Validators.required, Validators.maxLength(100)]],
    emissionsNumeratorUom: [null, Validators.required],
    emissionsDenominatorUom: [null, Validators.required],
    emissionsCalcMethodCode: ['', Validators.required],
    totalEmissions: ['', [Validators.required, numberValidator()]],
    emissionsUomCode: [null, Validators.required],
    comments: ['', [Validators.maxLength(400)]],
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
    private efService: EmissionFactorService,
    private lookupService: LookupService,
    private route: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    public formUtils: FormUtilsService,
    private modalService: NgbModal,
    private toastr: ToastrService,
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

      this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;

      this.sharedService.emitChange(data.facilitySite);
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
        this.emissionForm.get('emissionsFactorFormula').reset();
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

      // set epaEmissionFactor to true for EPA calculation methods
      if (value && value.epaEmissionFactor) {
        this.epaEmissionFactor = true;
      } else {
        this.emissionForm.get('formulaIndicator').reset(false);
        this.epaEmissionFactor = false;
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
      this.toastr.success('','Total emissions successfully calculated',{positionClass: 'toast-top-right'});
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
    if (!this.emissionForm.valid || (this.totalManualEntry && !this.emissionForm.controls.comments.value)) {
      this.emissionForm.markAllAsTouched();
      if (this.totalManualEntry && !this.emissionForm.controls.comments.value) {
        this.toastr.error('', 'Comments field must be populated for this pollutant ', {positionClass: 'toast-top-right'});
      }
    } else {

      const saveEmission = new Emission();
      Object.assign(saveEmission, this.emissionForm.value);

      if (this.createMode) {

        saveEmission.reportingPeriodId = this.reportingPeriod.id;

        this.emissionService.create(saveEmission)
        .subscribe(result => {

          this.sharedService.updateReportStatusAndEmit(this.route);
          this.router.navigate([this.processUrl]);
        });
      } else {

        saveEmission.id = this.emission.id;

        this.emissionService.update(saveEmission)
        .subscribe(result => {

          this.sharedService.updateReportStatusAndEmit(this.route);
          this.router.navigate([this.processUrl]);
        });
      }
    }
  }

  openSearchEfModal() {
    if (!this.emissionForm.get('pollutant').valid || !this.emissionForm.get('emissionsCalcMethodCode').valid) {
      this.emissionForm.get('pollutant').markAsTouched();
      this.emissionForm.get('emissionsCalcMethodCode').markAsTouched();
    } else {
      const efCriteria = new EmissionFactor();
      efCriteria.sccCode = +this.process.sccCode;
      efCriteria.pollutantCode = this.emissionForm.get('pollutant').value.pollutantCode;

      // set controlIndicator based on which calculation method is selected
      if (this.emissionForm.get('emissionsCalcMethodCode').value.controlIndicator) {
        efCriteria.controlIndicator = true;
      } else {
        efCriteria.controlIndicator = false;
      }

      this.efService.search(efCriteria)
      .subscribe(result => {
        const modalRef = this.modalService.open(EmissionFactorModalComponent, { size: 'xl', backdrop: 'static' });
        modalRef.componentInstance.tableData = result;

        // update form when modal closes successfully
        modalRef.result.then((modalEf: EmissionFactor) => {
          if (modalEf) {
            this.emissionForm.get('formulaIndicator').setValue(modalEf.formulaIndicator);
            this.emissionForm.get('emissionsFactor').setValue(modalEf.emissionFactor);
            this.emissionForm.get('emissionsFactorFormula').setValue(modalEf.emissionFactorFormula);
            this.emissionForm.get('emissionsNumeratorUom').setValue(modalEf.emissionsNumeratorUom);
            this.emissionForm.get('emissionsDenominatorUom').setValue(modalEf.emissionsDenominatorUom);
            this.emissionForm.get('emissionsUomCode').setValue(modalEf.emissionsNumeratorUom);
            // temporary to bypass validation, will be replace when actual values are added.
            if (modalEf.formulaIndicator) {
              this.emissionForm.get('emissionsFactor').setValue(1);
            }
          }
        }, () => {
          // needed for dismissing without errors
        });
      });
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
