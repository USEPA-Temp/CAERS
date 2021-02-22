import {Component, OnInit, OnChanges, Input} from '@angular/core';
import {FormBuilder, Validators, ValidatorFn, FormGroup, ValidationErrors} from '@angular/forms';
import {LookupService} from 'src/app/core/services/lookup.service';
import {BaseCodeLookup} from 'src/app/shared/models/base-code-lookup';
import {ReportingPeriod} from 'src/app/shared/models/reporting-period';
import {FormUtilsService} from 'src/app/core/services/form-utils.service';
import {UnitMeasureCode} from 'src/app/shared/models/unit-measure-code';
import {legacyUomValidator} from 'src/app/modules/shared/directives/legacy-uom-validator.directive';
import {SharedService} from 'src/app/core/services/shared.service';
import {ToastrService} from 'ngx-toastr';
import { OperatingStatus } from 'src/app/shared/enums/operating-status';
import { FuelUseSccCode } from 'src/app/shared/models/fuel-use-scc-code';

@Component({
    selector: 'app-edit-process-reporting-period-panel',
    templateUrl: './edit-process-reporting-period-panel.component.html',
    styleUrls: ['./edit-process-reporting-period-panel.component.scss']
})
export class EditProcessReportingPeriodPanelComponent implements OnInit, OnChanges {
  @Input() reportingPeriod: ReportingPeriod;
  @Input() sccCode: string;
  @Input() processOpStatus: string;
  isFuelUseScc = false;
  reportingPeriodForm = this.fb.group({
    reportingPeriodTypeCode: [{code: 'A'}, Validators.required],
    emissionsOperatingTypeCode: [null, Validators.required],
    calculationParameterTypeCode: [null, Validators.required],
    calculationParameterValue: ['', [
      Validators.required,
      Validators.min(0),
      Validators.pattern('^[0-9]*\\.?[0-9]+$')
    ]],
    calculationParameterUom: [null, [Validators.required, legacyUomValidator()]],
    calculationMaterialCode: [null, Validators.required],
    fuelUseValue: ['', [
      Validators.min(0),
      Validators.pattern('^[0-9]*\\.?[0-9]+$')
    ]],
    fuelUseUom: [null, [legacyUomValidator(), legacyUomValidator()]],
    fuelUseMaterialCode: [null],
    heatContentValue: ['', [
      Validators.min(0),
      Validators.pattern('^[0-9]*\\.?[0-9]+$')
    ]],
    heatContentUom: [null, [legacyUomValidator(), legacyUomValidator()]],
    comments: [null, Validators.maxLength(400)]
  }, { validators: [
    this.checkFuelUseFields(),
    this.checkHeatCapacityUom()
  ]});

  materialValues: BaseCodeLookup[];
  fuelUseMaterialValues: BaseCodeLookup[];
  sccFuelUse: FuelUseSccCode;
  sccFuelUseMaterialValue: BaseCodeLookup;
  parameterTypeValues: BaseCodeLookup[];
  operatingStatusValues: BaseCodeLookup[];
  reportingPeriodValues: BaseCodeLookup[] = [];
  uomValues: UnitMeasureCode[];
  denominatorUomValues: UnitMeasureCode[];
  fuelUseUomValues: UnitMeasureCode[];
  sccFuelUseUomValues: UnitMeasureCode[];
  heatContentUomValues: UnitMeasureCode[];
  showFuelDataCopyMessage = false;
  sccFuelUsefieldsWarning = null;
  sccHeatContentfieldsWarning = null;
  fuelMaterialInvalid = null;
  fuelUomInvalid = null;

  constructor(
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    private sharedService: SharedService,
    private toastr: ToastrService,
    private fb: FormBuilder) { }

  ngOnInit() {

        this.lookupService.retrieveCalcMaterial()
            .subscribe(result => {
                this.materialValues = result;
            });

        this.lookupService.retrieveFuelUseMaterial()
            .subscribe(result => {
                this.fuelUseMaterialValues = result;
            });

        this.lookupService.retrieveCalcParam()
            .subscribe(result => {
                this.parameterTypeValues = result;
            });

        this.lookupService.retrieveEmissionsOperatingType()
            .subscribe(result => {
                this.operatingStatusValues = result;
            });

        this.lookupService.retrieveReportingPeriod()
            .subscribe(result => {
                const annual = result.find(period => period.code === 'A');
                this.reportingPeriodValues.push(annual);
                this.reportingPeriodForm.get('reportingPeriodTypeCode').patchValue(annual);
            });

        this.lookupService.retrieveUom()
            .subscribe(result => {
                this.uomValues = result;
                this.denominatorUomValues = this.uomValues.filter(val => val.efDenominator);
            });

        this.lookupService.retrieveFuelUseUom()
            .subscribe(result => {
                this.fuelUseUomValues = result;
                this.heatContentUomValues = this.fuelUseUomValues.filter(val => val.heatContentUom);
            });

        if (this.sccCode) {
            this.getPointSourceScc(this.sccCode);
        }

        this.sharedService.processSccChangeEmitted$.subscribe(scc => {
            if (scc) {
                this.getPointSourceScc(scc);
            }
        });

        this.sharedService.processOpStatusChangeEmitted$.subscribe(opStatus => {
            if (opStatus) {
                this.processOpStatus = opStatus;
                this.disableWarning(this.processOpStatus);
            }
        });

        this.reportingPeriodForm.get('fuelUseMaterialCode').valueChanges
        .subscribe(value => {
            this.checkSccFuelMaterialUom();
        });

        this.reportingPeriodForm.get('fuelUseUom').valueChanges
        .subscribe(value => {
            this.checkSccFuelMaterialUom();
        });
    }

    ngOnChanges() {

        this.reportingPeriodForm.reset(this.reportingPeriod);
    }

    onSubmit() {

        // let period = new ReportingPeriod();
        // Object.assign(period, this.reportingPeriodForm.value);
        // console.log(period);
    }

    getPointSourceScc(processScc: string) {
        this.lookupService.retrievePointSourceSccCode(processScc)
        .subscribe(result => {
            if (result && result.fuelUseRequired) {
                this.isFuelUseScc = true;
                this.reportingPeriodForm.get('fuelUseValue').updateValueAndValidity();
                this.lookupService.retrieveSccFuelUseMaterial(processScc)
                    .subscribe(values => {
                        this.sccFuelUse = values;
                        this.sccFuelUseMaterialValue = values.calculationMaterialCode;
                        this.sccFuelUseUomValues = this.fuelUseUomValues.filter(
                            val => (this.sccFuelUse.fuelUseTypes.split(',')).includes(val.fuelUseType));
                        this.checkSccFuelMaterialUom();
                    });

            } else {
                this.isFuelUseScc = false;
            }
            this.disableWarning(this.processOpStatus);
        });
    }

    checkSccFuelMaterialUom() {
        const materialMessage = 'Please select a valid Fuel Material for the Process SCC.';
        const uomMessage = 'Please select a valid Fuel UoM for the reported Fuel Material.';

        if (this.isFuelUseScc && this.processOpStatus === OperatingStatus.OPERATING) {
            this.fuelMaterialInvalid = this.reportingPeriodForm.get('fuelUseMaterialCode').value?.code !== this.sccFuelUseMaterialValue.code ? materialMessage : null;
            this.fuelUomInvalid = this.sccFuelUseUomValues?.includes(this.reportingPeriodForm.get('fuelUseUom').value) ? null : uomMessage;
        }
    }

    disableWarning(opStatus: string) {
        if (this.isFuelUseScc && opStatus === OperatingStatus.OPERATING) {
            this.sccFuelUsefieldsWarning = 'Fuel data for this Process SCC must be reported. If this is a duplicate process for an Alternative Throughput, only report Fuel data for one of these Processes.';
            this.sccHeatContentfieldsWarning = 'Heat Content data for this Process SCC must be reported. If this is a duplicate process for an Alternative Throughput, only report Heat Content data for one of these Processes.';
        } else {
            this.sccFuelUsefieldsWarning = false;
            this.sccHeatContentfieldsWarning = false;
        }
    }

    checkHeatCapacityUom(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
        const heatContentValue = control.get('heatContentValue').value;
        const heatContentUom = control.get('heatContentUom').value;

        if ((heatContentUom || (heatContentValue !== null && heatContentValue !== ''))
            && (heatContentValue === '' || heatContentValue === null || !heatContentUom)) {
            return {heatContentInvalid: true};
        }
        return null;
        };
    }

    checkFuelUseFields(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
        const fuelValue = control.get('fuelUseValue').value;
        const fuelMaterial = control.get('fuelUseMaterialCode').value;
        const fuelUom = control.get('fuelUseUom').value;

        if ((fuelValue || fuelMaterial || fuelUom)
            && (fuelValue === null || fuelValue === '' || !fuelMaterial || !fuelUom)) {
            return {fuelUsefields: true};
        }
        return null;
        };
    }

    copyFuelDataToThroughput() {
        const fuelMaterial = this.reportingPeriodForm.get('fuelUseMaterialCode').value;
        const fuelValue = this.reportingPeriodForm.get('fuelUseValue').value;
        const fuelUom = this.reportingPeriodForm.get('fuelUseUom').value;

        this.reportingPeriodForm.get('calculationMaterialCode').patchValue(fuelMaterial);
        this.reportingPeriodForm.get('calculationParameterValue').patchValue(fuelValue);
        this.reportingPeriodForm.get('calculationParameterUom').patchValue(fuelUom);
        const message = 'Fuel data copied to Throughput data fields will be used to calculate total emissions for these pollutants.';
        this.toastr.success(message);
    }

    isFuelUseDirty = ():boolean => {
        const fuelMaterial = this.reportingPeriodForm.get('fuelUseMaterialCode');
        const fuelValue = this.reportingPeriodForm.get('fuelUseValue');
        const fuelUom = this.reportingPeriodForm.get('fuelUseUom');

        return fuelMaterial.dirty && fuelValue.dirty && fuelUom.dirty;
    }

}
