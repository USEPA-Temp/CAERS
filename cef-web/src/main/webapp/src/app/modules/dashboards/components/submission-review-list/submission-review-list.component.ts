import { Component, OnInit, Input } from '@angular/core';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { SubmissionUnderReview } from 'src/app/shared/models/submission-under-review';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReportSummaryModalComponent } from 'src/app/modules/dashboards/components/report-summary-modal/report-summary-modal.component';


@Component({
  selector: 'app-submission-review-list',
  templateUrl: './submission-review-list.component.html',
  styleUrls: ['./submission-review-list.component.scss']})

export class SubmissionReviewListComponent extends BaseSortableTable implements OnInit {

  @Input() tableData: SubmissionUnderReview[];
  @Input() hideSelectColumn: boolean;

  constructor(public userContext: UserContextService, private modalService: NgbModal) {
    super();
  }

  ngOnInit() {
  }

  openSummaryModal(submission: SubmissionUnderReview) {
    const modalWindow = this.modalService.open(ReportSummaryModalComponent, { size: 'lg' });
    modalWindow.componentInstance.submission = submission;
  }

}
