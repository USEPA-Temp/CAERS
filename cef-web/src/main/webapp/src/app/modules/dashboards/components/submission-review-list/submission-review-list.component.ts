/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { SubmissionUnderReview } from 'src/app/shared/models/submission-under-review';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReportSummaryModalComponent } from 'src/app/modules/dashboards/components/report-summary-modal/report-summary-modal.component';
import { ReportService } from 'src/app/core/services/report.service';
import { ReportDownloadService } from 'src/app/core/services/report-download.service';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-submission-review-list',
  templateUrl: './submission-review-list.component.html',
  styleUrls: ['./submission-review-list.component.scss']})

export class SubmissionReviewListComponent extends BaseSortableTable implements OnInit {

  @Input() tableData: SubmissionUnderReview[];
  @Input() reviewer: boolean;
  @Input() reportStatus: string;
  @Input() admin: boolean;
  @Output() refreshSubmissions = new EventEmitter();

  constructor(public userContext: UserContextService,
              private modalService: NgbModal,
              private reportService: ReportService,
              private reportDownloadService: ReportDownloadService,
			  private emissionsReportService: EmissionsReportingService,
      		  private toastr: ToastrService) {
    super();
  }

  ngOnInit() {
    this.controller.paginate = true;
  }

  openSummaryModal(submission: SubmissionUnderReview) {
    const modalWindow = this.modalService.open(ReportSummaryModalComponent, { size: 'lg' });
    modalWindow.componentInstance.submission = submission;
  }

  downloadReport(reportId: number, facilitySiteId: number, year: number, altFacilityId: number, reportStatus: string){
    this.reportService.retrieveReportDownloadDto(reportId, facilitySiteId)
    .subscribe(reportDownloadDto => {
      if ((reportStatus==='APPROVED') || (reportStatus==='SUBMITTED')) {
        this.reportDownloadService.downloadFile(reportDownloadDto, altFacilityId +'_'+
        year +'_' + 'Emissions_Report' + '_Final_Submission');
      } else {
        this.reportDownloadService.downloadFile(reportDownloadDto, altFacilityId +'_'+
        year +'_' + 'Emissions_Report' + '_Submission_In_Progress');
      }
    });
  }

  deleteReport(eisProgramId: string, reportId: number, reportYear: number) {
    this.emissionsReportService.delete(reportId).subscribe(() => {
      this.toastr.success('', 'The ' + reportYear + ' Emissions Report from this Facility (EIS Id ' + eisProgramId + ') has been deleted successfully.');
	  this.refreshSubmissions.emit();
    });
  }

  openDeleteModal(eisProgramId: string, reportId: number, reportYear: number) {
    const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
    const modalMessage = `Are you sure you want to delete the ${reportYear} Emissions Report from this Facility 
      					  (EIS Id ${eisProgramId})? This will also remove any Facility Information, Emission Units, 
						  Control Devices, and Release Point associated with this report.`;
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
      this.deleteReport(eisProgramId, reportId, reportYear);
    });
  }
}
