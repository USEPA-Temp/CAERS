import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SubmissionUnderReview } from 'src/app/shared/models/submission-under-review';
import { ReportSummary } from 'src/app/shared/models/report-summary';
import { ReportService } from 'src/app/core/services/report.service';

@Component({
  selector: 'app-report-summary-modal',
  templateUrl: './report-summary-modal.component.html',
  styleUrls: ['./report-summary-modal.component.scss']
})
export class ReportSummaryModalComponent implements OnInit {

    @Input() submission: SubmissionUnderReview;
    reportSummaryList: ReportSummary[];

    constructor(public activeModal: NgbActiveModal, private reportService: ReportService) { }


    ngOnInit() {
        if (this.submission.facilitySiteId) {
            this.reportService.retrieve(this.submission.year, this.submission.facilitySiteId)
            .subscribe(report => {
                console.log(report);
                this.reportSummaryList = report;
            });
        }
    }

    onClose() {
      this.activeModal.close();
    }

    onPrint() {
      alert('Print clicked');
    }

}
