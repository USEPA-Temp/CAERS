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
  submittedCount: number = 0;
  inProgress: SubmissionUnderReview[];
  inProgressCount: number = 0;
  advancedQA: SubmissionUnderReview[];
  advancedQACount: number = 0;
  approved: SubmissionUnderReview[];
  approvedCount: number = 0;
  currentUser: User;
  currentYear: any;

  constructor(private submissionsReviewDashboardService: SubmissionsReviewDashboardService,
              private sharedService: SharedService,
              public userContext: UserContextService) {
      this.currentYear = new Date().getFullYear() - 1;
      this.sharedService.submissionReviewChangeEmitted$
      .subscribe(submissions => {
        this.filterAndCountSubmissions(submissions);
      });
      this.userContext.getUser().subscribe(user => {
        this.currentUser = user;
        if (this.currentUser.role === 'Reviewer') {
          this.submissionsReviewDashboardService.retrieveAllFacilitiesReportsForCurrentReportingYear(this.currentYear)
          .subscribe(submissions => {
            this.filterAndCountSubmissions(submissions);
          });
        }
      });
    }

  ngOnInit() {
  }

  filterAndCountSubmissions(submissions){
      this.approvedCount = this.advancedQACount = this.submittedCount = this.inProgressCount = 0;
      submissions.forEach(submission => {
        if (submission.reportStatus === 'APPROVED') {
          this.approvedCount++; 
        }
        if (submission.reportStatus === 'SUBMITTED') {
          this.submittedCount++;
        }
        if (submission.reportStatus === 'IN_PROGRESS'){
          this.inProgressCount++;
        }
        if (submission.reportStatus === 'ADVANCED_QA'){
          this.advancedQACount++;
        }
      });
  }

}
