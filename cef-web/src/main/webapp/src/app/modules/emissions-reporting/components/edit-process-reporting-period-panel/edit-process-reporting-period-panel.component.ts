import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { LookupService } from 'src/app/core/services/lookup.service';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';
import { legacyUomValidator } from 'src/app/modules/shared/directives/legacy-uom-validator.directive';

@Component({
  selector: 'app-edit-process-reporting-period-panel',
  templateUrl: './edit-process-reporting-period-panel.component.html',
  styleUrls: ['./edit-process-reporting-period-panel.component.scss']
})
export class EditProcessReportingPeriodPanelComponent implements OnInit, OnChanges {
  @Input() reportingPeriod: ReportingPeriod;
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
    comments: [null, Validators.maxLength(400)]
  });

  materialValues: BaseCodeLookup[];
  parameterTypeValues: BaseCodeLookup[];
  operatingStatusValues: BaseCodeLookup[];
  reportingPeriodValues: BaseCodeLookup[] = [];
  uomValues: UnitMeasureCode[];
  denominatorUomValues: UnitMeasureCode[];

  constructor(
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    private fb: FormBuilder) { }

  ngOnInit() {

    this.lookupService.retrieveCalcMaterial()
    .subscribe(result => {
      this.materialValues = result;
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
  }

  ngOnChanges() {

    this.reportingPeriodForm.reset(this.reportingPeriod);
  }

  onSubmit() {

    // console.log(this.reportingPeriodForm);

    // let period = new ReportingPeriod();
    // Object.assign(period, this.reportingPeriodForm.value);
    // console.log(period);
  }

}
