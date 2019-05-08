
import {DecimalPipe} from '@angular/common';
import {Component, QueryList, ViewChildren} from '@angular/core';
import {Observable} from 'rxjs';

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

  constructor(public service: SubmissionReviewService) {
    this.submissionReviews$ = service.submissionReviews$;
    this.total$ = service.total$;
  }

  onSort({column, direction}: SortEvent) {
    // resetting other headers
    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    this.service.sortColumn = column;
    this.service.sortDirection = direction;
  }
}
