import { Component, OnInit } from '@angular/core';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { Input } from '@angular/core';
import { SubmissionUnderReview } from 'src/app/shared/models/submission-under-review';


@Component({
  selector: 'app-submission-review-list',
  templateUrl: './submission-review-list.component.html',
  styleUrls: ['./submission-review-list.component.scss']})

export class SubmissionReviewListComponent extends BaseSortableTable implements OnInit {

  @Input() tableData: SubmissionUnderReview[];

  constructor(public userContext: UserContextService) {
    super();
  }

  ngOnInit() {
  }

}
