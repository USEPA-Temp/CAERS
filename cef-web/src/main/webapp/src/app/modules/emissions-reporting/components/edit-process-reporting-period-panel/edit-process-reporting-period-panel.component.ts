import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { LookupService } from 'src/app/core/services/lookup.service';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';

@Component({
  selector: 'app-edit-process-reporting-period-panel',
  templateUrl: './edit-process-reporting-period-panel.component.html',
  styleUrls: ['./edit-process-reporting-period-panel.component.scss']
})
export class EditProcessReportingPeriodPanelComponent implements OnInit, OnChanges {
  @Input() reportingPeriod: ReportingPeriod;
  reportingPeriodForm = this.fb.group({
    reportingPeriodTypeCode: [null, Validators.required],
    emissionsOperatingTypeCode: [null, Validators.required],
    calculationParameterTypeCode: [null, Validators.required],
    calculationParameterValue: ['', [
      Validators.required,
      Validators.min(0),
      Validators.pattern('^[0-9]*\\.?[0-9]+$')
    ]],
    calculationParameterUom: [null, Validators.required],
    calculationMaterialCode: [null, Validators.required],
    comments: ['', Validators.maxLength(2000)]
  });

  materialValues: BaseCodeLookup[];
  parameterTypeValues: BaseCodeLookup[];
  operatingStatusValues: BaseCodeLookup[];
  reportingPeriodValues: BaseCodeLookup[];
  uomValues: BaseCodeLookup[];

  constructor(
    private lookupService: LookupService,
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

    this.lookupService.retrieveOperatingStatus()
    .subscribe(result => {
      this.operatingStatusValues = result;
    });

    this.lookupService.retrieveReportingPeriod()
    .subscribe(result => {
      this.reportingPeriodValues = result;
    });

    this.lookupService.retrieveUom()
    .subscribe(result => {
      this.uomValues = result;
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

  compareCode(c1: BaseCodeLookup, c2: BaseCodeLookup) {
    return c1 && c2 ? c1.code === c2.code : c1 === c2;
  }

}
