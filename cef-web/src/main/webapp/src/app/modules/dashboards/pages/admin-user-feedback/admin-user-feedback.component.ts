import { Component, OnInit } from '@angular/core';
import { UserFeedbackService } from 'src/app/core/services/user-feedback.service';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { FormControl } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserFeedbackReportModalComponent } from '../../components/user-feedback-report-modal/user-feedback-report-modal.component';
import { UserFeedback } from 'src/app/shared/models/user-feedback';
import { UserContextService } from 'src/app/core/services/user-context.service';

@Component({
  selector: 'app-admin-user-feedback',
  templateUrl: './admin-user-feedback.component.html',
  styleUrls: ['./admin-user-feedback.component.scss']
})
export class AdminUserFeedbackComponent extends BaseSortableTable implements OnInit {

  tableData: UserFeedback[];
  page = 1;
  pageSize = 25;

  cboFilterYear = new FormControl();
  cboFilterAgency = new FormControl();

  availableYears: number[];
  availableAgencies: string[];

  userAgency: string;

  intuitiveRateAvg = 0;
  dataEntryScreensAvg = 0;
  dataEntryBulkUploadAvg = 0;
  calculationScreensAvg = 0;
  controlsAndControlPathAssignAvg = 0;
  qualityAssuranceChecksAvg = 0;
  overallReportingTimeAvg = 0;

  constructor(private userFeedbackService: UserFeedbackService,
              private userService: UserContextService,
              private modalService: NgbModal) {
    super();
  }

  ngOnInit() {
    const CurrentYear = new Date().getFullYear() - 1;

    this.userService.getUser().subscribe(user => {
      this.userAgency = user.agencyCode;
    });

    this.availableYears = [];
    this.availableAgencies = [];

    this.tableData = [];

    this.availableYears.push(CurrentYear);
    this.availableAgencies.push(this.userAgency);

    this.cboFilterYear.valueChanges.subscribe(() => {
      this.retrieveData(this.cboFilterYear.value, this.cboFilterAgency.value);
    });

    this.cboFilterAgency.valueChanges.subscribe(() => {
      this.retrieveData(this.cboFilterYear.value, this.cboFilterAgency.value);
    });

    this.cboFilterYear.setValue(CurrentYear,
      {emitEvent: false, emitModelToViewChange: true, emitViewToModelChange: false});

    this.cboFilterAgency.setValue(this.userAgency,
      {emitEvent: false, emitModelToViewChange: true, emitViewToModelChange: false});

    this.retrieveOptions();
    this.retrieveData(this.cboFilterYear.value, this.cboFilterAgency.value);
  }

  retrieveOptions() {
    this.userFeedbackService.retrieveAvailableStats()
      .subscribe(userFB => {
        this.availableAgencies = userFB.availableAgencies;
        this.availableYears = userFB.availableYears;
      });
  }

  retrieveData(year, agency) {
    this.userFeedbackService.retrieveAllByYearAndAgency(year, agency)
      .subscribe(userFB => {
        this.tableData = userFB;
        this.retrieveStats(year, agency);
    });
  }

  retrieveStats(year, agency) {
    this.userFeedbackService.retrieveStatsByYearAndAgency(year, agency)
      .subscribe(userFB => {
        this.intuitiveRateAvg = +(userFB.intuitiveRateAvg !== null ? (userFB.intuitiveRateAvg) : 0);
        this.dataEntryScreensAvg = +(userFB.dataEntryScreensAvg !== null ? (userFB.dataEntryScreensAvg) : 0);
        this.dataEntryBulkUploadAvg = +(userFB.dataEntryBulkUploadAvg !== null ? (userFB.dataEntryBulkUploadAvg) : 0);
        this.calculationScreensAvg = +(userFB.calculationScreensAvg !== null ? (userFB.calculationScreensAvg) : 0);
        this.controlsAndControlPathAssignAvg = +(userFB.controlsAndControlPathAssignAvg !== null ? (userFB.controlsAndControlPathAssignAvg) : 0);
        this.qualityAssuranceChecksAvg = +(userFB.qualityAssuranceChecksAvg !== null ? (userFB.qualityAssuranceChecksAvg) : 0);
        this.overallReportingTimeAvg = +(userFB.overallReportingTimeAvg !== null ? (userFB.overallReportingTimeAvg) : 0);
      });
  }

  openFeedbackModal(feedback: UserFeedback) {
    const modalWindow = this.modalService.open(UserFeedbackReportModalComponent, { size: 'lg' });
    modalWindow.componentInstance.feedback = feedback;
  }

}
