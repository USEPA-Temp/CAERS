import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SubmissionUnderReview } from 'src/app/shared/models/submission-under-review';
import { ReportSummary } from 'src/app/shared/models/report-summary';
import { ReportService } from 'src/app/core/services/report.service';
import { ReportDownloadService } from 'src/app/core/services/report-download.service';

@Component({
  selector: 'app-report-summary-modal',
  templateUrl: './report-summary-modal.component.html',
  styleUrls: ['./report-summary-modal.component.scss']
})
export class ReportSummaryModalComponent implements OnInit {

    @Input() submission: SubmissionUnderReview;
    tableData: ReportSummary[];
    radiationData: ReportSummary[];
    emissionsReportYear: number;

    constructor(
      public activeModal: NgbActiveModal,
      private reportService: ReportService,
      private reportDownloadService: ReportDownloadService) { }


    ngOnInit() {
        if (this.submission.facilitySiteId) {
            this.emissionsReportYear = this.submission.year;
            this.reportService.retrieve(this.submission.year, this.submission.facilitySiteId)
            .subscribe(pollutants => {
              // filter out radiation pollutants to show separately at the end of the table
              // (only radionucleides right now which is code 605)
              this.tableData = pollutants.filter(pollutant => {
                  return pollutant.pollutantCode !== '605';
              });

              this.radiationData = pollutants.filter(pollutant => {
                  return pollutant.pollutantCode === '605';
              });
            });
        }
    }

    onClose() {
      this.activeModal.close();
    }

    onPrint() {
      alert('Print clicked');
    }

    downloadSummaryReport() {
        if ((this.submission.reportStatus === 'APPROVED') || (this.submission.reportStatus === 'SUBMITTED')) {
            this.reportDownloadService.downloadReportSummary(this.tableData, this.submission.altFacilityId + '_' +
            this.submission.year + '_' + 'Report_Summary' + '_Final_Submission');
        } else {
            this.reportDownloadService.downloadReportSummary(this.tableData, this.submission.altFacilityId + '_' +
            this.submission.year + '_' + 'Report_Summary' + '_Submission_In_Progress');
        }
    }

}
