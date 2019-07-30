import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { LookupService } from 'src/app/core/services/lookup.service';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';

@Component({
  selector: 'app-edit-process-reporting-period-panel',
  templateUrl: './edit-process-reporting-period-panel.component.html',
  styleUrls: ['./edit-process-reporting-period-panel.component.scss']
})
export class EditProcessReportingPeriodPanelComponent implements OnInit {
  reportingPeriodForm = this.fb.group({
    reportingPeriodTypeCode: [''],
    emissionsOperatingTypeCode: [''],
    calculationParameterTypeCode: [''],
    calculationParameterValue: [''],
    calculationParameterUom: [],
    calculationMaterialCode: [''],
    comments: ['']
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
      this.parameterTypeValues = result;
    });

    this.lookupService.retrieveCalcParam()
    .subscribe(result => {
      this.materialValues = result;
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

  onSubmit() {

    console.log(this.reportingPeriodForm);

    let period = new ReportingPeriod();
    Object.assign(period, this.reportingPeriodForm.value);
    console.log(period);
  }

}
