import { Component, OnInit, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { CefFacility } from '../../model/cef-facility';
import {SortableHeader, SortEvent} from '../../sortable.directive';
import { FacilityService } from '../../services/facility.service';
import { UserContextService } from '../../services/user-context.service';

export const compare = (v1, v2) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

@Component({
  selector: 'app-submission-review-list',
  templateUrl: './submission-review-list.component.html',
  styleUrls: ['./submission-review-list.component.scss']})
export class SubmissionReviewListComponent {

  cefFacilities: CefFacility[] = [];
  clientId: string = '';

  @ViewChildren(SortableHeader) headers: QueryList<SortableHeader>;

  constructor(private facilityService: FacilityService, public userContext: UserContextService) {
    this.clientId = userContext.user.agencyCode;
  }

  ngOnInit() {
    this.getData();
  }

  getData({}: void) {
    this.facilityService.getFacilitiesByState(this.clientId)
    .subscribe(result => {
      this.cefFacilities = result;
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
    } else {
      this.cefFacilities = [...this.cefFacilities].sort((a, b) => {
        const res = compare(a[column], b[column]);
        return direction === 'asc' ? res : -res;
      });
    }
  }
}
