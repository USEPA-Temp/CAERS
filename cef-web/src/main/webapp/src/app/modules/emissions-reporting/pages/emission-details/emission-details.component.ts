import { Component, OnInit, Input} from '@angular/core';
import { Emission } from 'src/app/shared/models/emission';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { Process } from 'src/app/shared/models/process';
import { Validators, FormBuilder, ValidatorFn, FormGroup, FormControl, ValidationErrors } from '@angular/forms';
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
import { EmissionFormulaVariable } from 'src/app/shared/models/emission-formula-variable';
import { VariableValidationType } from 'src/app/shared/enums/variable-validation-type';
import { EmissionFormulaVariableCode } from 'src/app/shared/models/emission-formula-variable-code';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { ControlPollutantTableComponent } from '../../components/control-pollutant-table/control-pollutant-table.component';
import { legacyUomValidator } from 'src/app/modules/shared/directives/legacy-uom-validator.directive';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { legacyItemValidator } from 'src/app/modules/shared/directives/legacy-item-validator.directive';

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
  epaEmissionFactor = false;
  efNumeratorMismatch = false;
  calcParamValue = true;
  failedNumDesc: string;
  failedTotalDesc: string;
  efDenominatorMismatch = false;
  failedRpCalcDesc: string;
  failedDenomDesc: string;
  needsCalculation = false;
  formulaVariables: EmissionFormulaVariableCode[] = [];

  readOnlyMode = true;

  processUrl: string;
  unitIdentifier: string;

  emissionForm = this.fb.group({
    pollutant: [null, [Validators.required]],
    formulaIndicator: [false, Validators.required],
    emissionsFactor: ['', [Validators.required]],
    emissionsFactorFormula: [''],
    emissionsFactorText: ['', [Validators.required, Validators.maxLength(100)]],
    emissionsNumeratorUom: [null, [Validators.required, legacyUomValidator()]],
    emissionsDenominatorUom: [null, [Validators.required, legacyUomValidator()]],
    emissionsCalcMethodCode: ['', Validators.required],
    totalManualEntry: [false, Validators.required],
    overallControlPercent: ['', [Validators.min(0), Validators.max(99.999999)]],
    totalEmissions: ['', [Validators.required, Validators.min(0)]],
    emissionsUomCode: [null, [Validators.required, legacyUomValidator()]],
    comments: [null, [Validators.maxLength(400)]],
    calculationComment: ['', [Validators.required, Validators.maxLength(4000)]],
    formulaVariables: this.fb.group({}),
  }, { validators: [
    this.emissionsCalculatedValidator(),
    this.emissionFactorGreaterThanZeroValidator(),
    this.pollutantEmissionsUoMValidator(),
    this.checkPercentSulfurRange(),
    this.checkPercentAshRange(),
    this.overallControlPercentValidator(),
    this.checkSignificantFigures()
    ]
  });


  methodValues: CalculationMethodCode[];
  pollutantValues: Pollutant[];
  uomValues: UnitMeasureCode[];
  numeratorUomValues: UnitMeasureCode[];
  denominatorUomValues: UnitMeasureCode[];
  currentCalcMethod: CalculationMethodCode;

  constructor(
    private emissionService: EmissionService,
    private emissionUnitService: EmissionUnitService,
    private periodService: ReportingPeriodService,
    private processService: EmissionsProcessService,
    private efService: EmissionFactorService,
    private lookupService: LookupService,
    private userContextService: UserContextService,
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

    this.lookupService.retrieveUom()
    .subscribe(result => {
      this.uomValues = result;
      this.numeratorUomValues = this.uomValues.filter(val => val.efNumerator);
      this.denominatorUomValues = this.uomValues.filter(val => val.efDenominator);
    });

    this.route.data
    .subscribe(data => {

      const year = data.facilitySite.emissionsReport.year;

      this.emissionForm.get('pollutant').setValidators([Validators.required, legacyItemValidator(year, 'Pollutant', 'pollutantName')]);

      this.lookupService.retrieveCurrentPollutants(year)
      .subscribe(result => {
        this.pollutantValues = result;
      });


      this.createMode = data.create === 'true';
      this.editable = data.create === 'true';

      this.userContextService.getUser().subscribe( user => {
        if (user.role !== 'Reviewer' && ReportStatus.IN_PROGRESS === data.facilitySite.emissionsReport.status) {
          this.readOnlyMode = false;
        }
      });

      this.sharedService.emitChange(data.facilitySite);
    });
    this.route.paramMap
    .subscribe(params => {

      if (!this.createMode) {
        this.emissionService.retrieve(+params.get('emissionId'))
        .subscribe(result => {
          this.emission = result;

          this.emissionForm.reset(this.emission);
          this.setupVariableFormFromValues(this.emission.variables);
          this.emissionForm.disable();
          this.currentCalcMethod = this.emissionForm.get('emissionsCalcMethodCode').value;
        });
      } else {
        this.emissionForm.enable();

        this.setupForm();

      }

      this.periodService.retrieve(+params.get('periodId'))
      .subscribe(result => {
        this.reportingPeriod = result;

        this.processService.retrieve(this.reportingPeriod.emissionsProcessId)
        .subscribe(process => {
          this.process = process;

          this.processUrl = `/facility/${params.get('facilityId')}/report/${params.get('reportId')}/${BaseReportUrl.EMISSIONS_PROCESS}/${this.process.id}`;

          this.emissionUnitService.retrieve(process.emissionsUnitId)
          .subscribe(unit => {
            this.unitIdentifier = unit.unitIdentifier;
          });
        });
      });
    });
  }

  private onMethodChange(value: CalculationMethodCode, status: string, forceReset: boolean) {

    if ('DISABLED' !== status) {
      if (value && value.totalDirectEntry) {
        this.emissionForm.get('emissionsFactor').reset({value: null, disabled: true});
        this.emissionForm.get('emissionsFactorFormula').reset({value: null, disabled: true});
        this.emissionForm.get('emissionsFactorText').reset({value: null, disabled: true});
        this.emissionForm.get('emissionsNumeratorUom').reset({value: null, disabled: true});
        this.emissionForm.get('emissionsDenominatorUom').reset({value: null, disabled: true});
        this.isCommentRequired();
        this.getTotalManualEntry().setValue(false);
      } else {
        this.emissionForm.get('emissionsFactor').enable();
        this.emissionForm.get('emissionsFactorText').enable();
        this.emissionForm.get('emissionsNumeratorUom').enable();
        this.emissionForm.get('emissionsDenominatorUom').enable();
        this.isCommentRequired();
        this.getTotalManualEntry().setValue(false);
      }

      const sameCalcMethod = (this.currentCalcMethod && this.currentCalcMethod.code === value.code);
      if (!sameCalcMethod) {
        this.currentCalcMethod = value;
      }

      // set epaEmissionFactor to true for EPA calculation methods
      if (value && value.epaEmissionFactor) {
        this.epaEmissionFactor = true;
        if (!sameCalcMethod || forceReset) {
          this.emissionForm.get('emissionsFactor').reset();
          this.emissionForm.get('emissionsFactorText').reset();
          this.emissionForm.get('emissionsFactorFormula').reset();
          this.emissionForm.get('formulaIndicator').reset();
          this.emissionForm.get('formulaVariables').reset();
        }
      } else {
        this.emissionForm.get('formulaIndicator').reset(false);
        this.setupVariableForm([]);
        this.epaEmissionFactor = false;
      }

    }
  }

  private setupForm() {

    const calcMethod = this.getCalcMethodCodeValue();
    if (calcMethod && calcMethod.totalDirectEntry) {
      this.emissionForm.get('emissionsFactor').reset({value: null, disabled: true});
      this.emissionForm.get('emissionsFactorFormula').reset({value: null, disabled: true});
      this.emissionForm.get('emissionsFactorText').reset({value: null, disabled: true});
      this.emissionForm.get('emissionsNumeratorUom').reset({value: null, disabled: true});
      this.emissionForm.get('emissionsDenominatorUom').reset({value: null, disabled: true});
      this.emissionForm.get('calculationComment').reset({value: null, disabled: true});
      this.isCommentRequired();
      this.getTotalManualEntry().setValue(false);
    } else {
      this.emissionForm.get('emissionsFactor').enable();
      this.emissionForm.get('emissionsFactorText').enable();
      this.emissionForm.get('emissionsNumeratorUom').enable();
      this.emissionForm.get('emissionsDenominatorUom').enable();
      this.isCommentRequired();
      if (this.getTotalManualEntry().value) {
        this.emissionForm.get('calculationComment').enable();
        this.emissionForm.get('emissionsFactorText').setValidators(null);
      } else {
        this.emissionForm.get('calculationComment').reset({value: null, disabled: true});
        this.emissionForm.get('emissionsFactorText').setValidators([Validators.required]);
      }
    }

    // set epaEmissionFactor to true for EPA calculation methods
    if (calcMethod && calcMethod.epaEmissionFactor) {
      this.epaEmissionFactor = true;
    } else {
      this.emissionForm.get('formulaIndicator').reset(false);
      this.setupVariableForm([]);
      this.epaEmissionFactor = false;
    }

    // set validators of emissionsFactorText to null if the "i prefer to calculate the total emissions" check box is selected
    if (this.emissionForm.get('totalManualEntry').value) {
        this.emissionForm.get('emissionsFactorText').setValidators(null);
        this.emissionForm.controls.emissionsFactorText.updateValueAndValidity();
    }


    // Reconfigure form after calculation method changes
    this.emissionForm.get('emissionsCalcMethodCode').valueChanges
    .subscribe(value => {
      this.onMethodChange(value, this.emissionForm.get('emissionsCalcMethodCode').status, false);
    });

    // Make user calculate total emissions after changes
    this.setupCalculationFormListeners();

    this.emissionForm.get('formulaIndicator').valueChanges
    .subscribe(value => {
      if (value) {
        this.emissionForm.get('emissionsFactorFormula').enable();
      } else {
        this.emissionForm.get('emissionsFactorFormula').disable();
      }
    });
  }

  // reset ef and ef formula when pollutant is changed
  onChange() {
    if (this.emissionForm.value.emissionsCalcMethodCode) {
      this.onMethodChange(this.emissionForm.get('emissionsCalcMethodCode').value, this.emissionForm.get('emissionsCalcMethodCode').status, true);
    }
  }

  isCommentRequired() {
    const engJudgment = '2';
    if (this.emissionForm.value.emissionsCalcMethodCode && this.emissionForm.value.emissionsCalcMethodCode.code === engJudgment) {
        this.emissionForm.get('comments').setValidators([Validators.required]);
        this.emissionForm.get('comments').updateValueAndValidity();
      } else {
        this.emissionForm.get('comments').clearValidators();
        this.emissionForm.get('comments').updateValueAndValidity();
      }
  }

  onCalculate() {
    if (!this.canCalculate()) {
      this.emissionForm.markAllAsTouched();
    } else {
      if (!(this.reportingPeriod.calculationParameterUom && this.emissionForm.get('emissionsDenominatorUom').value
            && this.reportingPeriod.calculationParameterUom.unitType === this.emissionForm.get('emissionsDenominatorUom').value.unitType)) {
        this.failedRpCalcDesc = this.reportingPeriod.calculationParameterUom.description;
        this.failedDenomDesc = this.emissionForm.get('emissionsDenominatorUom').value.description;
        this.efDenominatorMismatch = true;
      } else {
        this.efDenominatorMismatch = false;
        this.failedRpCalcDesc = null;
        this.failedDenomDesc = null;
      }

      if (!(this.emissionForm.get('emissionsUomCode').value && this.emissionForm.get('emissionsNumeratorUom').value
            && this.emissionForm.get('emissionsUomCode').value.unitType === this.emissionForm.get('emissionsNumeratorUom').value.unitType)) {
        this.failedNumDesc = this.emissionForm.get('emissionsNumeratorUom').value.description;
        this.failedTotalDesc = this.emissionForm.get('emissionsUomCode').value.description;
        this.efNumeratorMismatch = true;
      } else {
        this.efNumeratorMismatch = false;
        this.failedNumDesc = null;
        this.failedTotalDesc = null;
      }

      if (this.reportingPeriod.calculationParameterValue < 0) {
        this.calcParamValue = false;
      } else {
        this.calcParamValue = true;
      }

      if (!(this.efNumeratorMismatch || this.efDenominatorMismatch) && this.reportingPeriod.calculationParameterValue >= 0) {
        const calcEmission = new Emission();
        Object.assign(calcEmission, this.emissionForm.value);

        calcEmission.variables = this.generateFormulaVariableDtos();
        calcEmission.reportingPeriodId = this.reportingPeriod.id;

        this.emissionService.calculateEmissionTotal(calcEmission)
          .subscribe(result => {

            this.needsCalculation = false;
            if (result.formulaIndicator) {
              this.emissionForm.get('emissionsFactor').setValue(result.emissionsFactor, {emitEvent: false});
            }
            this.emissionForm.get('totalEmissions').setValue(result.totalEmissions);
            this.toastr.success('', 'Total emissions successfully calculated');

          });

      }
    }
  }

  onCancelEdit() {
    this.emissionForm.enable();
    if (this.createMode) {
      this.router.navigate([this.processUrl]);
    } else {
      this.emissionService.retrieve(this.emission.id)
      .subscribe(result => {
        this.emission = result;
        this.currentCalcMethod = this.emission.emissionsCalcMethodCode;
        this.emissionForm.reset(this.emission);
        this.setupVariableFormFromValues(this.emission.variables);
        this.emissionForm.disable();
      });
    }
    this.editable = false;
  }

  onEdit() {
    this.editable = true;
    // this prevents events from triggering that shouldn't be.
    this.emissionForm.enable({emitEvent: false});
    this.setupForm();

    // reset carryover variables
    this.needsCalculation = false;
    this.efNumeratorMismatch = false;
    this.failedNumDesc = null;
    this.failedTotalDesc = null;
    this.efDenominatorMismatch = false;
    this.failedRpCalcDesc = null;
    this.failedDenomDesc = null;

  }

  onSubmit() {
    if (this.emissionForm.value.formulaIndicator && this.getTotalManualEntry().value) {
      this.emissionForm.get('emissionsFactor').disable();
    }

    if (!this.emissionForm.valid) {
      this.emissionForm.markAllAsTouched();
    } else {
      this.editable = false;

      const saveEmission = new Emission();
      Object.assign(saveEmission, this.emissionForm.value);

      saveEmission.variables = this.generateFormulaVariableDtos();
      if (this.emission) {
        // Match variable with existing id for variable
        saveEmission.variables.forEach(sv => {
          const oldVar = this.emission.variables.find(ov => {
            return sv.variableCode.code === ov.variableCode.code;
          });
          if (oldVar) {
            sv.id = oldVar.id;
          }
        });
      }

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
          this.emissionService.retrieve(this.emission.id)
          .subscribe(result => {
            this.emission = result;
            this.currentCalcMethod = this.emission.emissionsCalcMethodCode;
            this.emissionForm.reset(this.emission);
            this.setupVariableFormFromValues(this.emission.variables);
            this.emissionForm.disable();
          });
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
      if (this.getCalcMethodCodeValue().controlIndicator) {
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

            let descriptionText = modalEf.description;
            if (descriptionText && descriptionText.length > 100) {
              descriptionText = descriptionText.substring(0, 97);
              descriptionText = descriptionText.concat('...');
            }
            this.emissionForm.get('emissionsFactorText').setValue(descriptionText);

            this.setupVariableForm(modalEf.variables || []);
          }
        }, () => {
          // needed for dismissing without errors
        });
      });
    }

  }

  canCalculate() {
    return this.emissionForm.get('formulaIndicator').valid
        &&  (this.emissionForm.get('formulaIndicator').value || this.emissionForm.get('emissionsFactor').valid)
        && this.emissionForm.get('emissionsNumeratorUom').valid
        && this.emissionForm.get('emissionsDenominatorUom').valid
        && this.emissionForm.get('overallControlPercent').valid
        && this.emissionForm.get('emissionsUomCode').valid
        && this.emissionForm.get('formulaVariables').valid
        && this.emissionForm.get('totalManualEntry').valid;
  }

  setupCalculationFormListeners() {
    this.emissionForm.get('emissionsFactor').valueChanges
    .subscribe(value => {
      if (this.emissionForm.enabled) {
        this.needsCalculation = true;
      }
    });

    this.emissionForm.get('emissionsFactorFormula').valueChanges
    .subscribe(value => {
      if (this.emissionForm.enabled) {
        this.needsCalculation = true;
      }
    });

    this.emissionForm.get('formulaVariables').valueChanges
    .subscribe(value => {
      if (this.emissionForm.enabled) {
        this.needsCalculation = true;
      }
    });

    this.emissionForm.get('emissionsNumeratorUom').valueChanges
    .subscribe(value => {
      if (this.emissionForm.enabled) {
        this.needsCalculation = true;
      }
    });

    this.emissionForm.get('emissionsDenominatorUom').valueChanges
    .subscribe(value => {
      if (this.emissionForm.enabled) {
        this.needsCalculation = true;
      }
    });

    this.emissionForm.get('overallControlPercent').valueChanges
    .subscribe(value => {
      if (this.emissionForm.enabled) {
        this.needsCalculation = true;
      }
    });

    this.emissionForm.get('totalManualEntry').valueChanges
    .subscribe(value => {
      if (this.emissionForm.enabled) {
        this.emissionForm.get('emissionsFactorText').setValidators(null);
        this.emissionForm.controls.emissionsFactorText.updateValueAndValidity();
        this.needsCalculation = true;
        if (value && this.getCalcMethodCodeValue() && !this.getCalcMethodCodeValue().totalDirectEntry) {
          this.emissionForm.get('calculationComment').enable();
          this.emissionForm.get('emissionsFactorText').setValidators(null);
        } else {
          this.emissionForm.get('emissionsFactorText').setValidators([Validators.required]);
          this.emissionForm.controls.emissionsFactorText.updateValueAndValidity();
          this.emissionForm.get('calculationComment').disable();
        }
      }
    });

    this.emissionForm.get('emissionsUomCode').valueChanges
    .subscribe(value => {
      if (this.emissionForm.enabled) {
        this.needsCalculation = true;
      }
    });
  }

  getTotalManualEntry() {
    return this.emissionForm.get('totalManualEntry');
  }

  getCalcMethodCodeValue(): CalculationMethodCode {
    return this.emissionForm.value.emissionsCalcMethodCode;
  }

  getFormulaVariableForm() {
    return this.emissionForm.get('formulaVariables') as FormGroup;
  }

  private setupVariableFormFromValues(values: EmissionFormulaVariable[]) {
    this.setupVariableForm(values.map(v => v.variableCode));

    values.forEach(v => {
      this.getFormulaVariableForm().get(v.variableCode.code).reset(v.value);
    });
  }

  private setupVariableForm(newVars: EmissionFormulaVariableCode[]) {

    const formKeys = Object.keys(this.getFormulaVariableForm().controls);
    const varCodes = newVars.map(v => {
      return v.code;
    });

    // Remove unneeded variables while leaving existing values
    formKeys.forEach(key => {
      if (!varCodes.includes(key)) {
        this.getFormulaVariableForm().removeControl(key);
      }
    });

    this.formulaVariables = newVars;

    newVars.forEach(v => {
      if (VariableValidationType.PERCENT === v.validationType) {
        this.getFormulaVariableForm().addControl(v.code,
            new FormControl(null, [Validators.required, Validators.min(0), Validators.max(100)]));
      } else if (VariableValidationType.CASU === v.validationType) {
        this.getFormulaVariableForm().addControl(v.code,
            new FormControl(null, [Validators.required, Validators.min(1.5), Validators.max(7)]));
      } else if (VariableValidationType.DAY_PER_YEAR === v.validationType) {
        this.getFormulaVariableForm().addControl(v.code,
            new FormControl(null, [Validators.required, Validators.min(0), Validators.max(365)]));
      }
      this.getFormulaVariableForm().addControl(v.code, new FormControl(null, Validators.required));
    });
  }

  private generateFormulaVariableDtos(): EmissionFormulaVariable[] {

    const formulaValues: EmissionFormulaVariable[] = [];

    this.formulaVariables.forEach(fv => {
      formulaValues.push(new EmissionFormulaVariable(this.getFormulaVariableForm().get(fv.code).value, fv));
    });
    return formulaValues;
  }


  searchPollutants = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => this.pollutantValues && this.pollutantValues.filter(v => v.pollutantName.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || v.pollutantCode.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || (v.pollutantCasId ? v.pollutantCasId.toLowerCase().indexOf(term.toLowerCase()) > -1 : false))
                                        .slice(0, 20))
    )

  pollutantFormatter = (result: Pollutant) => `${result.pollutantName}  -  ${result.pollutantCode} ${result.pollutantCasId ? ' - ' + result.pollutantCasId : ''}`;

  emissionsCalculatedValidator(): ValidatorFn {
    return (control: FormGroup): {[key: string]: any} | null => {
      const efControl = control.get('emissionsFactor');
      if (efControl.enabled && !control.get('totalManualEntry').value) {
        return this.needsCalculation ? {emissionsCalculated: true} : null;
      }
      return null;
    };
  }

  emissionFactorGreaterThanZeroValidator(): ValidatorFn {
    return (control: FormGroup): {[key: string]: any} | null => {
      const emissionFactor = control.get('emissionsFactor');
      if (emissionFactor.enabled && emissionFactor.value <= 0) {
        return {efFactorLessThanOrEqualToZero: true};
      } else {
        return null;
      }
    };
  }

  pollutantEmissionsUoMValidator(): ValidatorFn {
    return (control: FormGroup): {[key: string]: any} | null => {
      const pollutant = control.get('pollutant');
      if (pollutant !== null && pollutant.value !== undefined && pollutant.value !== null && control.get('emissionsUomCode') !== null) {
        if ((pollutant.value.pollutantCode !== undefined && pollutant.value.pollutantCode === '605')
        && control.get('emissionsUomCode').value !== null && control.get('emissionsUomCode').value.code !== 'CURIE') {
          return {emissionsUomCodeCurie: true};
        }
        return null;
      }
    };
  }

  checkPercentSulfurRange(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const emissionFormulaVar = control.get('formulaVariables');
      if (emissionFormulaVar !== undefined && emissionFormulaVar !== null && Object.keys(emissionFormulaVar.value)[0] === "SU") {
        if (emissionFormulaVar.value.SU !== null && (emissionFormulaVar.value.SU < 0.01 || emissionFormulaVar.value.SU > 10)) {
          return {invalidSulfurRange: true};
        }
        return null;
      }
    };
  }

  checkPercentAshRange(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const emissionFormulaVar = control.get('formulaVariables');
      if (emissionFormulaVar !== undefined && emissionFormulaVar !== null && Object.keys(emissionFormulaVar.value)[0] === "A") {
        if (emissionFormulaVar.value.A !== null && (emissionFormulaVar.value.A < 0.01 || emissionFormulaVar.value.A > 30)) {
          return {invalidAshRange: true};
        }
        return null;
      }
    };
  }

  overallControlPercentValidator(): ValidatorFn {
    return (control: FormGroup): {[key: string]: any} | null => {
      const overallControl = control.get('overallControlPercent');
      const calcMethod = control.get('emissionsCalcMethodCode');
      if (calcMethod.value.controlIndicator
      && (overallControl.value && overallControl.value !== 0)) {
        return {overallControlPercentInvalid: true};
      }
      return null;
    };
  }

  checkSignificantFigures(): ValidatorFn {
    return (control: FormGroup): {[key: string]: any} | null => {
      const totalEmissions = control.get('totalEmissions');
      if (totalEmissions.value && this.formUtils.getSignificantFigures(totalEmissions.value) > this.formUtils.emissionsMaxSignificantFigures) {
        return {significantFiguresInvalid: true};
      }
      return null;
    };
  }

}
