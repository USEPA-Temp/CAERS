import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { Process } from 'src/app/shared/models/process';
import { SharedService } from 'src/app/core/services/shared.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReportingPeriodService } from 'src/app/core/services/reporting-period.service';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ControlPathService } from 'src/app/core/services/control-path.service';
import { EditProcessInfoPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-info-panel/edit-process-info-panel.component';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { EditProcessOperatingDetailPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-operating-detail-panel/edit-process-operating-detail-panel.component';
import { EditProcessReportingPeriodPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-reporting-period-panel/edit-process-reporting-period-panel.component';
import { OperatingDetailService } from 'src/app/core/services/operating-detail.service';
import { OperatingDetail } from 'src/app/shared/models/operating-detail';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-emissions-process-details',
  templateUrl: './emissions-process-details.component.html',
  styleUrls: ['./emissions-process-details.component.scss']
})
export class EmissionsProcessDetailsComponent implements OnInit {
  process: Process;
  controlPaths: ControlPath[];
  facilitySite: FacilitySite;

  readOnlyMode = true;

  editInfo = false;
  editDetails = false;
  editPeriod = false;

  @ViewChild(EditProcessInfoPanelComponent, { static: false })
  infoComponent: EditProcessInfoPanelComponent;

  @ViewChild(EditProcessOperatingDetailPanelComponent, { static: false })
  operatingDetailsComponent: EditProcessOperatingDetailPanelComponent;

  @ViewChild(EditProcessReportingPeriodPanelComponent, { static: false })
  reportingPeriodComponent: EditProcessReportingPeriodPanelComponent;

  constructor(
    private processService: EmissionsProcessService,
    private reportingPeriodService: ReportingPeriodService,
    private operatingDetailService: OperatingDetailService,
    private controlPathService: ControlPathService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private sharedService: SharedService) { }

  ngOnInit() {
    this.route.paramMap
    .subscribe(map => {
      this.processService.retrieve(+map.get('processId'))
      .subscribe(process => {
        this.process = process;
        this.reportingPeriodService.retrieveForEmissionsProcess(this.process.id)
        .subscribe(periods => {
          this.process.reportingPeriods = periods;
        });
      });

      this.controlPathService.retrieveForEmissionsProcess(+map.get('processId'))
      .subscribe(controlPaths => {
        this.controlPaths = controlPaths;
      });
    });

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;
      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);
    });
  }

  setEditInfo(value: boolean) {
    this.editInfo = value;
  }

  setEditDetails(value: boolean) {
    this.editDetails = value;
  }

  setEditPeriod(value: boolean) {
    this.editPeriod = value;
  }

  updateProcess() {
    if (!this.infoComponent.processForm.valid) {
      this.infoComponent.processForm.markAllAsTouched();
    } else {
      const updatedProcess = new Process();

      Object.assign(updatedProcess, this.infoComponent.processForm.value);
      updatedProcess.emissionsUnitId = this.process.emissionsUnitId;
      updatedProcess.id = this.process.id;

      this.processService.update(updatedProcess)
      .subscribe(result => {

        Object.assign(this.process, result);
        this.sharedService.updateReportStatusAndEmit(this.route);
        this.setEditInfo(false);
      });
    }
  }

  updateOperatingDetail(detail: OperatingDetail) {
    if (!this.operatingDetailsComponent.operatingDetailsForm.valid || !this.operatingDetailsComponent.validateOperatingPercent()) {
      this.operatingDetailsComponent.operatingDetailsForm.markAllAsTouched();
      if(!this.operatingDetailsComponent.validateOperatingPercent()){
        this.toastr.error('',"Total Operating Percent must be between 99.5 and 100.5",{positionClass: 'toast-top-right'})
      }
    } else {
      const updatedDetail = new OperatingDetail();

      Object.assign(updatedDetail, this.operatingDetailsComponent.operatingDetailsForm.value);

      updatedDetail.id = detail.id;

      this.operatingDetailService.update(updatedDetail)
      .subscribe(result => {

        Object.assign(detail, result);
        this.sharedService.updateReportStatusAndEmit(this.route);
        this.setEditDetails(false);
      });
    }
  }

  updateReportingPeriod(period: ReportingPeriod) {
    if (!this.reportingPeriodComponent.reportingPeriodForm.valid) {
      this.reportingPeriodComponent.reportingPeriodForm.markAllAsTouched();
    } else {
      const updatedReportingPeriod = new ReportingPeriod();

      Object.assign(updatedReportingPeriod, this.reportingPeriodComponent.reportingPeriodForm.value);

      updatedReportingPeriod.emissionsProcessId = this.process.id;
      updatedReportingPeriod.id = period.id;

      this.reportingPeriodService.update(updatedReportingPeriod)
      .subscribe(result => {

        Object.assign(period, result);
        this.sharedService.updateReportStatusAndEmit(this.route);
        this.setEditPeriod(false);
      });
    }
  }

}
