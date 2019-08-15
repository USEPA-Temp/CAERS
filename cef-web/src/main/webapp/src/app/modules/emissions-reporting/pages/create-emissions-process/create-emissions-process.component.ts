import { Component, OnInit, ViewChild } from '@angular/core';
import { Process } from 'src/app/shared/models/process';
import { ActivatedRoute, Router } from '@angular/router';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { EditProcessInfoPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-info-panel/edit-process-info-panel.component';
import { EditProcessOperatingDetailPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-operating-detail-panel/edit-process-operating-detail-panel.component';
import { EditProcessReportingPeriodPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-reporting-period-panel/edit-process-reporting-period-panel.component';
import { OperatingDetail } from 'src/app/shared/models/operating-detail';

@Component({
  selector: 'app-create-emissions-process',
  templateUrl: './create-emissions-process.component.html',
  styleUrls: ['./create-emissions-process.component.scss']
})
export class CreateEmissionsProcessComponent implements OnInit {
  emissionsUnit: EmissionUnit;

  @ViewChild(EditProcessInfoPanelComponent)
  private infoComponent: EditProcessInfoPanelComponent;

  @ViewChild(EditProcessOperatingDetailPanelComponent)
  private operatingDetailsComponent: EditProcessOperatingDetailPanelComponent;

  @ViewChild(EditProcessReportingPeriodPanelComponent)
  private reportingPeriodComponent: EditProcessReportingPeriodPanelComponent;

  constructor(
    private emissionUnitService: EmissionUnitService,
    private processService: EmissionsProcessService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {

    this.route.paramMap
      .subscribe(map => {
        this.emissionUnitService.retrieve(+map.get('unitId'))
        .subscribe(unit => {
          this.emissionsUnit = unit;
        });
    });
  }

  isValid() {
    return this.infoComponent.processForm.valid
        && this.operatingDetailsComponent.operatingDetailsForm.valid
        && this.reportingPeriodComponent.reportingPeriodForm.valid;
  }

  onSubmit() {

    // console.log(this.infoComponent.processForm.value);
    // console.log(this.operatingDetailsComponent.operatingDetailsForm.value);
    // console.log(this.reportingPeriodComponent.reportingPeriodForm.value);

    const process = new Process();
    const operatingDetails = new OperatingDetail();
    const reportingPeriod = new ReportingPeriod();

    Object.assign(process, this.infoComponent.processForm.value);
    Object.assign(operatingDetails, this.operatingDetailsComponent.operatingDetailsForm.value);
    Object.assign(reportingPeriod, this.reportingPeriodComponent.reportingPeriodForm.value);

    reportingPeriod.operatingDetails = [operatingDetails];
    process.reportingPeriods = [reportingPeriod];
    process.emissionsUnitId = this.emissionsUnit.id;

    // console.log(process);

    // console.log(JSON.stringify(process));

    this.processService.create(process)
    .subscribe(result => {
      // console.log(result);
      this.router.navigate(['../..'], { relativeTo: this.route });
    });

  }

}