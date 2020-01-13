import { Component, OnInit, ViewChild } from '@angular/core';
import { SubmissionsReviewDashboardService } from 'src/app/core/services/submissions-review-dashboard.service';
import { SubmissionUnderReview } from 'src/app/shared/models/submission-under-review';
import { SubmissionReviewListComponent } from 'src/app/modules/dashboards/components/submission-review-list/submission-review-list.component';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SubmissionReviewModalComponent } from 'src/app/modules/dashboards/components/submission-review-modal/submission-review-modal.component';
import {SharedService} from "../../../../core/services/shared.service";

@Component( {
    selector: 'app-submission-review-dashboard',
    templateUrl: './submission-review-dashboard.component.html',
    styleUrls: ['./submission-review-dashboard.component.scss']
} )
export class SubmissionReviewDashboardComponent implements OnInit {

    @ViewChild(SubmissionReviewListComponent, {static: true})
    private listComponent: SubmissionReviewListComponent;

    submissions: SubmissionUnderReview[] = [];
    hideButtons: boolean;
    invalidSelection = false;
    currentYear: number;
    selectedYear: string;

    constructor(
        private emissionReportService: EmissionsReportingService,
        private submissionsReviewDashboardService: SubmissionsReviewDashboardService,
        private modalService: NgbModal,
        private sharedService: SharedService ) { }

    ngOnInit() {
        this.currentYear = new Date().getFullYear() - 1;
        this.selectedYear = 'currentYear';
        this.getSubmissionsUnderReview();
    }

    onApprove() {
        const selectedSubmissions = this.listComponent.tableData.filter(item => item.checked).map(item => item.emissionsReportId);

        if (!selectedSubmissions.length) {
            this.invalidSelection = true;
        } else {
            this.invalidSelection = false;
            const modalRef = this.modalService.open(SubmissionReviewModalComponent, { size: 'lg', backdrop: 'static' });
            modalRef.componentInstance.title = 'Accept Submissions';
            modalRef.componentInstance.message = 'Would you like to accept the selected submissions?';

            modalRef.result.then((comments) => {
                this.emissionReportService.acceptReports(selectedSubmissions, comments)
                .subscribe(() => {
                    this.getSubmissionsUnderReview();
                    this.emitAllSubmissions();
                });
            }, () => {
                // needed for dismissing without errors
            });
        }
    }

    onReject() {
        const selectedSubmissions = this.listComponent.tableData.filter(item => item.checked).map(item => item.emissionsReportId);

        if (!selectedSubmissions.length) {
            this.invalidSelection = true;
        } else {
            this.invalidSelection = false;
            const modalRef = this.modalService.open(SubmissionReviewModalComponent, { size: 'lg', backdrop: 'static' });
            modalRef.componentInstance.title = 'Reject Submissions';
            modalRef.componentInstance.message = 'Would you like to reject the selected submissions?';

            modalRef.result.then((comments) => {
                this.emissionReportService.rejectReports(selectedSubmissions,comments)
                .subscribe(() => {
                    this.getSubmissionsUnderReview();
                    this.emitAllSubmissions();
                });
            }, () => {
                // needed for dismissing without errors
            });
        }
    }

    getSubmissionsUnderReview(): void {
        this.submissionsReviewDashboardService.retrieveFacilitiesReportsUnderReview()
            .subscribe( submissions => this.submissions = submissions.filter(item => item.reportStatus === 'SUBMITTED' && item.year === this.currentYear));
    }

    onStatusSelected(value: string) {
        if (value === 'Submitted') {
            this.hideButtons = false;
        } else {
            this.hideButtons = true;
        }
        if (this.selectedYear === 'currentYear') {
            this.submissionsReviewDashboardService.retrieveFacilitiesReportsUnderReview()
            .subscribe( submissions =>{ this.submissions = submissions.filter(item => (item.reportStatus === value.toUpperCase()) && (item.year === this.currentYear)) 
            });
        } else {
            this.submissionsReviewDashboardService.retrieveFacilitiesReportsUnderReview()
            .subscribe( submissions =>{ this.submissions = submissions.filter(item => item.reportStatus === value.toUpperCase())
            });
        }
    }

    onYearSelected(year: string, value: string) {
        this.selectedYear = year;
        this.onStatusSelected(value);
    }

    // emits the updated submission list to the notification component
    emitAllSubmissions(): void {
        this.submissionsReviewDashboardService.retrieveFacilitiesReportsUnderReview().subscribe(submissions => {
            this.sharedService.emitSubmissionChange(submissions);
        });
    }

}
