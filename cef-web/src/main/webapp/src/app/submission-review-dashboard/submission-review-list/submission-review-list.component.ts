import { DecimalPipe } from '@angular/common';
import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { Observable } from 'rxjs';

import { SubmissionReview } from '../../model/submission-review';
import { SubmissionReviewService } from '../../services/submission-review.service';
import {SortableHeader, SortEvent} from '../../sortable.directive';

@Component({
  selector: 'app-submission-review-list',
  templateUrl: './submission-review-list.component.html',
  styleUrls: ['./submission-review-list.component.scss'],
  providers: [SubmissionReviewService, DecimalPipe]})
export class SubmissionReviewListComponent {
  submissionReviews$: Observable<SubmissionReview[]>;
  total$: Observable<number>;

  @ViewChildren(SortableHeader) headers: QueryList<SortableHeader>;

  constructor(private submissionReviewService: SubmissionReviewService) {
  }

  ngOnInit() {
    this.submissionReviews$ = this.submissionReviewService.submissionReviews$;
    this.total$ = this.submissionReviewService.total$;
  }

  onSort({column, direction}: SortEvent) {
    // resetting other headers
    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    this.submissionReviewService.sortColumn = column;
    this.submissionReviewService.sortDirection = direction;
  }
}
