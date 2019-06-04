import { Component, OnInit, Directive, EventEmitter, Input, Output, QueryList, ViewChildren } from '@angular/core';
import { CefFacility } from 'src/app/shared/models/cef-facility';
import { FacilitySiteService } from 'src/app/core/http/facility/facility-site.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';

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
