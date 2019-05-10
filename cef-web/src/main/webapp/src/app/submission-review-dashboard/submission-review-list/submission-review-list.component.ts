import { DecimalPipe } from '@angular/common';
import { Component, OnInit, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { Observable } from 'rxjs';

import { SubmissionReview } from '../../model/submission-review';
import {SortableHeader, SortEvent} from '../../sortable.directive';
import { FacilityService } from '../../services/facility.service';

export type SortDirection = 'asc' | 'desc' | '';
const rotate: {[key: string]: SortDirection} = { 'asc': 'desc', 'desc': '', '': 'asc' };
export const compare = (v1, v2) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

export interface SortEvent {
  column: string;
  direction: SortDirection;
}

@Directive({
  selector: 'th[sortable]',
  host: {
    '[class.asc]': 'direction === "asc"',
    '[class.desc]': 'direction === "desc"',
    '(click)': 'rotate()'
  }
})
export class NgbdSortableHeader {

  @Input() sortable: string;
  @Input() direction: SortDirection = '';
  @Output() sort = new EventEmitter<SortEvent>();

  rotate() {
    this.direction = rotate[this.direction];
    this.sort.emit({column: this.sortable, direction: this.direction});
  }
}

@Component({
  selector: 'app-submission-review-list',
  templateUrl: './submission-review-list.component.html',
  styleUrls: ['./submission-review-list.component.scss']})
export class SubmissionReviewListComponent {
  submissionReviews$: Observable<SubmissionReview[]>;
  total$: Observable<number>;
  submissionReviews: SubmissionReview[] = [];
  page = 1;
  pageSize = 5;
  collectionSize = 0;

  @ViewChildren(SortableHeader) headers: QueryList<SortableHeader>;

  constructor(private facilityService: FacilityService) {
    this.getData();
  }

  ngOnInit() {
  }

  getData({}: void) {
    this.facilityService.getFacilitiesByState('GA')
    .subscribe(result => {
      this.submissionReviews = result;
      this.collectionSize = this.submissionReviews.length;
      this.setPage();
    });
  }

  onSort({column, direction}: SortEvent) {

    // resetting other headers
    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    // sorting countries 
    if (direction === '') {
      this.submissionReviews = this.submissionReviews;
      this.setPage();
    } else {
      this.submissionReviews = [...this.submissionReviews].sort((a, b) => {
        const res = compare(a[column], b[column]);
        return direction === 'asc' ? res : -res;
      });
    }
  }

  
  setPage({}: void) {
    return this.submissionReviews
      .map((review, i) => ({id: i + 1, ...review}))
      .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
  }
  
}
