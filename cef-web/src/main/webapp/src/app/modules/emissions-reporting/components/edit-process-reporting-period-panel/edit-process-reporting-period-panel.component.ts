import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { FormBuilder, Validators, ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';
import { LookupService } from 'src/app/core/services/lookup.service';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';
import { legacyUomValidator } from 'src/app/modules/shared/directives/legacy-uom-validator.directive';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-edit-process-reporting-period-panel',
  templateUrl: './edit-process-reporting-period-panel.component.html',
  styleUrls: ['./edit-process-reporting-period-panel.component.scss']
})
export class EditProcessReportingPeriodPanelComponent implements OnInit, OnChanges {
  @Input() reportingPeriod: ReportingPeriod;
  @Input() sccCode: string;
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
    fuelUseUom: [null, [legacyUomValidator()]],
    fuelUseMaterialCode: [null],
    heatContentValue: ['', [
      Validators.min(0),
      Validators.pattern('^[0-9]*\\.?[0-9]+$')
    ]],
    heatContentUom: [null, [legacyUomValidator()]],
    comments: [null, Validators.maxLength(400)]
  }, { validators: [
    this.checkFuelUseFields(),
    this.checkHeatCapacityUom()
  ]});

  materialValues: BaseCodeLookup[];
  fuelUseMaterialValues: BaseCodeLookup[];
  parameterTypeValues: BaseCodeLookup[];
  operatingStatusValues: BaseCodeLookup[];
  reportingPeriodValues: BaseCodeLookup[] = [];
  uomValues: UnitMeasureCode[];
  denominatorUomValues: UnitMeasureCode[];
  fuelUseUomValues: UnitMeasureCode[];
  heatContentUomValues: UnitMeasureCode[];
  showFuelDataCopyMessage = false;

  constructor(
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    private sharedService: SharedService,
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
      // this.reportingPeriodValues = result;
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
        } else {
          this.isFuelUseScc = false;
        }

      });
  }

  checkHeatCapacityUom(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const heatContentValue = control.get('heatContentValue').value;
      const heatContentUom = control.get('heatContentUom').value;

      if (this.isFuelUseScc && (!heatContentValue || !heatContentUom)) {
        return {sccHeatContentfields: true};
      }
      if (!this.isFuelUseScc && ((heatContentValue && !heatContentUom) || (!heatContentValue && heatContentUom))) {
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

      if (this.isFuelUseScc && (!fuelValue || !fuelMaterial || !fuelUom)) {
        return {sccFuelUsefields: true};
      }

      if (!this.isFuelUseScc && (fuelValue || fuelMaterial || fuelUom)
        && (fuelValue === null || fuelValue === '' || !fuelMaterial || !fuelUom)) {
        return {fuelUsefields: true};
      }
      return null;
    };
  }

  copyFuelDataToThroughput() {
      const fuelMaterial = this.reportingPeriodForm.get('fuelUseMaterialCode').value;
      const fuelValue = this.reportingPeriodForm.get('fuelUseValue').value;
      const fuelUom = this.reportingPeriodForm.get('fuelUseUom').value
      console.log(fuelMaterial, fuelValue, fuelUom)

      this.reportingPeriodForm.get('calculationMaterialCode').patchValue(fuelMaterial);
      this.reportingPeriodForm.get('calculationParameterValue').patchValue(fuelValue);
      this.reportingPeriodForm.get('calculationParameterUom').patchValue(fuelUom);
      this.showFuelDataCopyMessage = true;

  }

  isFuelUseDirty = ():boolean => {
      const fuelMaterial = this.reportingPeriodForm.get('fuelUseMaterialCode');
      const fuelValue = this.reportingPeriodForm.get('fuelUseValue');
      const fuelUom = this.reportingPeriodForm.get('fuelUseUom');

      return fuelMaterial.dirty && fuelValue.dirty && fuelUom.dirty;

  }

}
