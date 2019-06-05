import { Component, OnInit, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { CefFacility } from '../../model/cef-facility';
import { FacilitySiteService } from '../../services/facility-site.service';
import {SortableHeaderDirective, SortEvent, compare} from '../../shared/sortable.directive';
import { UserContextService } from '../../services/user-context.service';
import { BaseSortableTable } from '../../shared/sortable-table/base-sortable-table';

@Component({
  selector: 'app-submission-review-list',
  templateUrl: './submission-review-list.component.html',
  styleUrls: ['./submission-review-list.component.scss']})

export class SubmissionReviewListComponent extends BaseSortableTable implements OnInit {

  tableData: CefFacility[] = [];
  clientId = '';

  constructor(private facilitySiteService: FacilitySiteService, public userContext: UserContextService) {
    super();
    this.clientId = userContext.user.agencyCode;
  }

  ngOnInit() {
    this.getData();
  }

  getData() {
    this.facilitySiteService.getByState(this.clientId)
    .subscribe(result => {
      this.tableData = result;
    });
  }

}
