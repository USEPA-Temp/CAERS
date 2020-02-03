import { Component, OnInit, ViewChild } from '@angular/core';
import { SubmissionsReviewDashboardService } from 'src/app/core/services/submissions-review-dashboard.service';
import { SubmissionUnderReview } from 'src/app/shared/models/submission-under-review';
import { SubmissionReviewListComponent } from 'src/app/modules/dashboards/components/submission-review-list/submission-review-list.component';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SubmissionReviewModalComponent } from 'src/app/modules/dashboards/components/submission-review-modal/submission-review-modal.component';
import {SharedService} from "../../../../core/services/shared.service";
import { faTheaterMasks } from '@fortawesome/free-solid-svg-icons';

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
        private sharedService: SharedService) { }

    ngOnInit() {
        const CURRENT_REPORTING_YEAR = 'CURRENT_REPORTING_YEAR';
        this.currentYear = new Date().getFullYear() - 1;
        this.selectedYear = CURRENT_REPORTING_YEAR;
            this.retrieveFacilitiesReportsByYearAndStatus(this.currentYear, "SUBMITTED");
    }

    onApprove(year) {
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
                    if (year === 'ALL_YEARS') {
                        this.retrieveFacilitiesReportsByReportStatus('SUBMITTED');
                    }
                    if (year === 'CURRENT_REPORTING_YEAR') {
                        this.retrieveFacilitiesReportsByYearAndStatus(this.currentYear,'SUBMITTED');
                    }
                    this.emitAllSubmissions();
                });
            }, () => {
                // needed for dismissing without errors
            });
        }
    }

    onReject(year) {
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
                    if (year === 'ALL_YEARS') {
                        this.retrieveFacilitiesReportsByReportStatus('SUBMITTED');
                    }
                    if (year === 'CURRENT_REPORTING_YEAR') {
                        this.retrieveFacilitiesReportsByYearAndStatus(this.currentYear,'SUBMITTED');
                    }
                    this.emitAllSubmissions();
                });
            }, () => {
                // needed for dismissing without errors
            });
        }
    }

    retrieveFacilitiesReportsByYearAndStatus(year,reportStatus): void{
        this.submissionsReviewDashboardService.retrieveFacilitiesReportsByYearAndStatus(year, reportStatus)
            .subscribe( submissions => this.submissions = submissions);
    }

    retrieveFacilitiesReportsByReportStatus(reportStatus): void{
        this.submissionsReviewDashboardService.retrieveFacilitiesReportsUnderReviewByStatus(reportStatus)
            .subscribe(submissions => this.submissions = submissions);
    }

    onStatusSelected(value: string) {
        if (value === 'SUBMITTED') {
            this.hideButtons = false;
        } else {
            this.hideButtons = true;
        }
        if (this.selectedYear === 'CURRENT_REPORTING_YEAR') {
            this.retrieveFacilitiesReportsByYearAndStatus(this.currentYear,value);
        } else {
            this.retrieveFacilitiesReportsByReportStatus(value);
        }
    }

    onYearSelected(year: string, value: string) {
        this.selectedYear = year;
        this.onStatusSelected(value);
    }

    // emits the updated submission list to the notification component
    emitAllSubmissions(): void {
        this.submissionsReviewDashboardService.retrieveAllFacilitiesReportsForCurrentReportingYear(this.currentYear)
        .subscribe(submissions =>{
            this.sharedService.emitSubmissionChange(submissions);
        });
    }

}