import { Component, OnInit, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { CefFacility } from '../../model/cef-facility';
import {SortableHeader, SortEvent} from '../../sortable.directive';
import { FacilityService } from '../../services/facility.service';

export const compare = (v1, v2) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

@Component({
  selector: 'app-submission-review-list',
  templateUrl: './submission-review-list.component.html',
  styleUrls: ['./submission-review-list.component.scss']})
export class SubmissionReviewListComponent {

  cefFacilities: CefFacility[] = [];
  page = 1;
  pageSize = 5;
  collectionSize = 0;

  @ViewChildren(SortableHeader) headers: QueryList<SortableHeader>;

  constructor(private facilityService: FacilityService) {
  }

  ngOnInit() {
    this.getData();
  }

  getData({}: void) {
    this.facilityService.getFacilitiesByState('GA')
    .subscribe(result => {
      this.cefFacilities = result;
      this.collectionSize = this.cefFacilities.length;
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
      this.cefFacilities = this.cefFacilities;
      this.setPage();
    } else {
      this.cefFacilities = [...this.cefFacilities].sort((a, b) => {
        const res = compare(a[column], b[column]);
        return direction === 'asc' ? res : -res;
      });
      this.setPage();
    }
  }

  
  setPage({}: void) {
    alert('page: ' + this.page + '; pageSize: ' + this.pageSize + '; collectionSize: ' + this.collectionSize);
    return this.cefFacilities
      .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
  }
  
}
