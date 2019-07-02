import { Component, OnInit } from '@angular/core';
import { SubmissionsReviewDashboardService } from "src/app/core/services/submissions-review-dashboard.service";
import { SubmissionUnderReview } from "src/app/shared/models/submission-under-review";

@Component( {
    selector: 'app-submission-review-dashboard',
    templateUrl: './submission-review-dashboard.component.html',
    styleUrls: ['./submission-review-dashboard.component.scss']
} )
export class SubmissionReviewDashboardComponent implements OnInit {

    submissions: SubmissionUnderReview[] = [];

    constructor( private submissionsReviewDashboardService: SubmissionsReviewDashboardService ) { }

    ngOnInit() {
        this.getSubmissionsUnderReview();
    }

    getSubmissionsUnderReview(): void {
        this.submissionsReviewDashboardService.retrieveFacilitiesReportsUnderReview()
            .subscribe( submissions => this.submissions = submissions );
    }

}
