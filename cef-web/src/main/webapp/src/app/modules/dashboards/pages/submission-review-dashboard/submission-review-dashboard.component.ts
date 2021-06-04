import { Component, OnInit, ViewChild } from '@angular/core';
import { SubmissionsReviewDashboardService } from 'src/app/core/services/submissions-review-dashboard.service';
import { SubmissionUnderReview } from 'src/app/shared/models/submission-under-review';
import { SubmissionReviewListComponent } from 'src/app/modules/dashboards/components/submission-review-list/submission-review-list.component';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SubmissionReviewModalComponent } from 'src/app/modules/dashboards/components/submission-review-modal/submission-review-modal.component';
import {SharedService} from "src/app/core/services/shared.service";
import { FileAttachmentModalComponent } from 'src/app/modules/shared/components/file-attachment-modal/file-attachment-modal.component';
import { ReportStatus } from 'src/app/shared/enums/report-status';

@Component( {
    selector: 'app-submission-review-dashboard',
    templateUrl: './submission-review-dashboard.component.html',
    styleUrls: ['./submission-review-dashboard.component.scss']
} )
export class SubmissionReviewDashboardComponent implements OnInit {

    @ViewChild(SubmissionReviewListComponent, {static: true})

    private listComponent: SubmissionReviewListComponent;

    allSubmissions: SubmissionUnderReview[] = [];
    submissions: SubmissionUnderReview[] = [];
    hideButtons: boolean;
    invalidSelection = false;
    currentYear: number;
    selectedYear: string;
    selectedReportStatus = ReportStatus.SUBMITTED;
    selectedIndustrySector: string;

    industrySectors: string[] = [];

    constructor(
        private emissionReportService: EmissionsReportingService,
        private submissionsReviewDashboardService: SubmissionsReviewDashboardService,
        private modalService: NgbModal,
        private sharedService: SharedService) { }

    ngOnInit() {
        const CURRENT_REPORTING_YEAR = 'CURRENT_REPORTING_YEAR';
        this.currentYear = new Date().getFullYear() - 1;
        this.selectedYear = CURRENT_REPORTING_YEAR;
        this.retrieveFacilitiesReportsByYearAndStatus(this.currentYear, 'SUBMITTED');
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
                    this.refreshFacilityReports();
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
            const modalRef = this.modalService.open(FileAttachmentModalComponent, { size: 'lg', backdrop: 'static' });
            modalRef.componentInstance.reportId = selectedSubmissions[0];
            modalRef.componentInstance.title = 'Reject Submissions';
            modalRef.componentInstance.message = 'Would you like to reject the selected submissions?';

            modalRef.result.then((resp) => {
                this.emissionReportService.rejectReports(selectedSubmissions, resp.comments, resp.id)
                .subscribe(() => {
                    this.refreshFacilityReports()
                    this.emitAllSubmissions();
                });
            }, () => {
                // needed for dismissing without errors
            });
        }
    }

    refreshFacilityReports(): void {
        if (this.selectedYear === 'CURRENT_REPORTING_YEAR') {
            this.retrieveFacilitiesReportsByYearAndStatus(this.currentYear, this.selectedReportStatus);
        } else {
            this.retrieveFacilitiesReportsByReportStatus(this.selectedReportStatus);
        }
    }

    retrieveFacilitiesReportsByYearAndStatus(year, reportStatus): void {
        this.submissionsReviewDashboardService.retrieveFacilitiesReportsByYearAndStatus(year, reportStatus)
            .subscribe((submissions) => {
                this.sortSubmissions(submissions)
            });
    }

    retrieveFacilitiesReportsByReportStatus(reportStatus): void {
        this.submissionsReviewDashboardService.retrieveFacilitiesReportsUnderReviewByStatus(reportStatus)
            .subscribe((submissions) => {
                this.sortSubmissions(submissions);
            });
    }

    sortSubmissions(submissions: SubmissionUnderReview[]) {
        this.allSubmissions = submissions.sort((a, b) => (a.facilityName > b.facilityName) ? 1 : -1);
        // map down to industry values, convert to a Set to get distinct values, remove nulls, and then sort
        this.industrySectors = [...new Set(this.allSubmissions.map(item => item.industry))].filter(item => item != null).sort();
        this.filterSubmissions();
    }

    filterSubmissions() {
        // reset selected sector if the current selection is no longer in the dropdown
        if (this.selectedIndustrySector && !this.industrySectors.includes(this.selectedIndustrySector)) {
            this.selectedIndustrySector = null;
        }
        if (this.selectedIndustrySector) {
            this.submissions = this.allSubmissions.filter(item => item.industry === this.selectedIndustrySector);
        } else {
            this.submissions = this.allSubmissions;
        }
    }

    onStatusSelected(value) {
        this.selectedReportStatus = value;

        if (value === 'SUBMITTED') {
            this.hideButtons = false;
        } else {
            this.hideButtons = true;
        }
        this.refreshFacilityReports();
    }

    onYearSelected(year: string, value: string) {
        this.selectedYear = year;
        this.onStatusSelected(value);
    }

    onIndustrySelected(industry: string) {
        this.selectedIndustrySector = industry;
        this.filterSubmissions();
    }

    // emits the updated submission list to the notification component
    emitAllSubmissions(): void {
        this.submissionsReviewDashboardService.retrieveAllFacilitiesReportsForCurrentReportingYear(this.currentYear)
        .subscribe(submissions => {
            this.sharedService.emitSubmissionChange(submissions);
        });
    }

}
