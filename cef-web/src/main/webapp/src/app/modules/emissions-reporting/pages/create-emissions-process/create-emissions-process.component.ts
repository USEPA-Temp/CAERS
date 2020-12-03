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
import { ToastrService } from 'ngx-toastr';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-create-emissions-process',
  templateUrl: './create-emissions-process.component.html',
  styleUrls: ['./create-emissions-process.component.scss']
})
export class CreateEmissionsProcessComponent implements OnInit {
  emissionsUnit: EmissionUnit;

  @ViewChild(EditProcessInfoPanelComponent, { static: true })
  private infoComponent: EditProcessInfoPanelComponent;

  @ViewChild(EditProcessOperatingDetailPanelComponent, { static: true })
  private operatingDetailsComponent: EditProcessOperatingDetailPanelComponent;

  @ViewChild(EditProcessReportingPeriodPanelComponent, { static: true })
  private reportingPeriodComponent: EditProcessReportingPeriodPanelComponent;

  constructor(
    private emissionUnitService: EmissionUnitService,
    private processService: EmissionsProcessService,
    private route: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService,
    private toastr: ToastrService) { }

  ngOnInit() {

    this.route.paramMap
      .subscribe(map => {
        this.emissionUnitService.retrieve(+map.get('unitId'))
        .subscribe(unit => {
          this.emissionsUnit = unit;
        });
    });

    this.route.data
      .subscribe(data => {
        this.sharedService.emitChange(data.facilitySite);
      });
  }

  isValid() {
    return this.infoComponent.processForm.valid
        && this.operatingDetailsComponent.operatingDetailsForm.valid
        && this.reportingPeriodComponent.reportingPeriodForm.valid
        && this.operatingDetailsComponent.validateOperatingPercent();
  }

  onSubmit() {

    // console.log(this.infoComponent.processForm.value);
    // console.log(this.operatingDetailsComponent.operatingDetailsForm.value);
    // console.log(this.reportingPeriodComponent.reportingPeriodForm.value);

    if (!this.isValid()) {
      this.infoComponent.processForm.markAllAsTouched();
      this.operatingDetailsComponent.operatingDetailsForm.markAllAsTouched();
      this.reportingPeriodComponent.reportingPeriodForm.markAllAsTouched();
      if (!this.operatingDetailsComponent.validateOperatingPercent()) {
        this.toastr.error('', 'Total Operating Percent must be between 99.5 and 100.5');
      }
    } else {
      this.reportingPeriodComponent.reportingPeriodForm.get('reportingPeriodTypeCode').enable();
      const process = new Process();
      const operatingDetails = new OperatingDetail();
      const reportingPeriod = new ReportingPeriod();

      Object.assign(process, this.infoComponent.processForm.value);
      Object.assign(operatingDetails, this.operatingDetailsComponent.operatingDetailsForm.value);
      Object.assign(reportingPeriod, this.reportingPeriodComponent.reportingPeriodForm.value);

      reportingPeriod.operatingDetails = [operatingDetails];
      process.reportingPeriods = [reportingPeriod];
      process.emissionsUnitId = this.emissionsUnit.id;
      process.emissionsProcessIdentifier = this.infoComponent.processForm.controls.emissionsProcessIdentifier.value.trim();

      // console.log(process);

      // console.log(JSON.stringify(process));

      this.processService.create(process)
      .subscribe(result => {
        // console.log(result);
        this.sharedService.updateReportStatusAndEmit(this.route);
        this.router.navigate(['../..'], { relativeTo: this.route });
      });
    }

  }

}
