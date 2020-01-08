import { Component, OnInit } from '@angular/core';
import { SubmissionUnderReview } from 'src/app/shared/models/submission-under-review';
import { SubmissionsReviewDashboardService } from 'src/app/core/services/submissions-review-dashboard.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { User } from 'src/app/shared/models/user';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss']
})
export class NotificationListComponent implements OnInit {

  summarySubmissions: SubmissionUnderReview[];
  submitted: SubmissionUnderReview[];
  submittedCount: number;
  inProgress: SubmissionUnderReview[];
  inProgressCount: number;
  approved: SubmissionUnderReview[];
  approvedCount: number;
  currentUser: User;
  constructor(private submissionsReviewDashboardService: SubmissionsReviewDashboardService,
              private sharedService: SharedService,
              public userContext: UserContextService) {
      this.sharedService.submissionReviewChangeEmitted$
      .subscribe(submissions => {
        this.approved = submissions.filter(item => item.reportStatus === 'APPROVED');
        this.approvedCount = this.approved.length;
        this.submitted = submissions.filter(item => item.reportStatus === 'SUBMITTED');
        this.submittedCount = this.submitted.length;
        this.inProgress = submissions.filter(item => item.reportStatus === 'IN_PROGRESS');
        this.inProgressCount = this.inProgress.length;
      });
      this.userContext.getUser().subscribe(user => {
        this.currentUser = user;
      });
    }

  ngOnInit() {
    this.retrieveReviewSubmissions();
  }

  retrieveReviewSubmissions(){
    this.submissionsReviewDashboardService.retrieveFacilitiesReportsUnderReview().subscribe(submissions => {
      this.approved = submissions.filter(item => item.reportStatus === 'APPROVED');
      this.approvedCount = this.approved.length;
      this.submitted = submissions.filter(item => item.reportStatus === 'SUBMITTED');
      this.submittedCount = this.submitted.length;
      this.inProgress = submissions.filter(item => item.reportStatus === 'IN_PROGRESS');
      this.inProgressCount = this.inProgress.length;
    });
  }

}
