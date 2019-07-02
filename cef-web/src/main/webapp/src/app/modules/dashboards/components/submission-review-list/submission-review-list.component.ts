import { Component, OnInit } from '@angular/core';
import { CefFacility } from 'src/app/shared/models/cef-facility';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { Input } from "@angular/core";
import { SubmissionUnderReview } from "src/app/shared/models/submission-under-review";


@Component({
  selector: 'app-submission-review-list',
  templateUrl: './submission-review-list.component.html',
  styleUrls: ['./submission-review-list.component.scss']})

export class SubmissionReviewListComponent extends BaseSortableTable implements OnInit {

  @Input() tableData: SubmissionUnderReview[];
  clientId = '';

  constructor(private facilitySiteService: FacilitySiteService, public userContext: UserContextService) {
    super();
    this.clientId = userContext.user.agencyCode;
  }

  ngOnInit() {
  }

}
