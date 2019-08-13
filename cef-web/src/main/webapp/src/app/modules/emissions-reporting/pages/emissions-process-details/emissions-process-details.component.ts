import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { Process } from 'src/app/shared/models/process';
import { SharedService } from 'src/app/core/services/shared.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReportingPeriodService } from 'src/app/core/services/reporting-period.service';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { ControlAssignmentService } from 'src/app/core/services/control-assignment.service';
import { EditProcessInfoPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-info-panel/edit-process-info-panel.component';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { EditProcessOperatingDetailPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-operating-detail-panel/edit-process-operating-detail-panel.component';
import { EditProcessReportingPeriodPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-reporting-period-panel/edit-process-reporting-period-panel.component';
import { OperatingDetailService } from 'src/app/core/services/operating-detail.service';
import { OperatingDetail } from 'src/app/shared/models/operating-detail';

@Component({
  selector: 'app-emissions-process-details',
  templateUrl: './emissions-process-details.component.html',
  styleUrls: ['./emissions-process-details.component.scss']
})
export class EmissionsProcessDetailsComponent implements OnInit {
  process: Process;
  controlAssignments: ControlAssignment[];

  editInfo = false;
  editDetails = false;
  editPeriod = false;

  @ViewChild(EditProcessInfoPanelComponent)
  infoComponent: EditProcessInfoPanelComponent;

  @ViewChild(EditProcessOperatingDetailPanelComponent)
  operatingDetailsComponent: EditProcessOperatingDetailPanelComponent;

  @ViewChild(EditProcessReportingPeriodPanelComponent)
  reportingPeriodComponent: EditProcessReportingPeriodPanelComponent;

  constructor(
    private processService: EmissionsProcessService,
    private reportingPeriodService: ReportingPeriodService,
    private operatingDetailService: OperatingDetailService,
    private controlAssignmentService: ControlAssignmentService,
    private route: ActivatedRoute,
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

      this.controlAssignmentService.retrieveForEmissionsProcess(+map.get('processId'))
      .subscribe(controlAssignments => {
        this.controlAssignments = controlAssignments;
      });
    });

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
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
    const updatedProcess = new Process();

    Object.assign(updatedProcess, this.infoComponent.processForm.value);
    updatedProcess.emissionsUnitId = this.process.emissionsUnitId;
    updatedProcess.id = this.process.id;

    this.processService.update(updatedProcess)
    .subscribe(result => {

      Object.assign(this.process, result);
      this.setEditInfo(false);
    });
  }

  updateOperatingDetail(detail: OperatingDetail) {
    const updatedDetail = new OperatingDetail();

    Object.assign(updatedDetail, this.operatingDetailsComponent.operatingDetailsForm.value);

    updatedDetail.id = detail.id;

    this.operatingDetailService.update(updatedDetail)
    .subscribe(result => {

      Object.assign(detail, result);
      this.setEditDetails(false);
    });
  }

  updateReportingPeriod(period: ReportingPeriod) {
    const updatedReportingPeriod = new ReportingPeriod();

    Object.assign(updatedReportingPeriod, this.reportingPeriodComponent.reportingPeriodForm.value);

    updatedReportingPeriod.emissionsProcessId = this.process.id;
    updatedReportingPeriod.id = period.id;

    this.reportingPeriodService.update(updatedReportingPeriod)
    .subscribe(result => {

      Object.assign(period, result);
      this.setEditPeriod(false);
    });
  }

}
