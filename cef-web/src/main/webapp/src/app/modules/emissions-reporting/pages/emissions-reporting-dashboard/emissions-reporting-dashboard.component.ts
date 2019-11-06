import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { BusyModalComponent } from 'src/app/shared/components/busy-modal/busy-modal.component';
import { DeleteDialogComponent } from 'src/app/shared/components/delete-dialog/delete-dialog.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UserContextService } from 'src/app/core/services/user-context.service';

@Component({
    selector: 'app-emissions-reporting-dashboard',
    templateUrl: './emissions-reporting-dashboard.component.html',
    styleUrls: ['./emissions-reporting-dashboard.component.scss']
})
export class EmissionsReportingDashboardComponent implements OnInit {
    facility: CdxFacility;
    reports: EmissionsReport[];

    @ViewChild('FailedToCreateMessageBox', {static: true})
    _failedToCreateTemplate: TemplateRef<any>;

    _failedToCreateRef: NgbModalRef;

    constructor(
        private reportService: EmissionsReportingService,
        private route: ActivatedRoute,
        private sharedService: SharedService,
        private modalService: NgbModal,
        public router: Router,
        public userContext: UserContextService) { }


    ngOnInit() {

        this.route.data
            .subscribe((data: { facility: CdxFacility }) => {
                this.facility = data.facility;
                if (this.facility) {
                    this.reportService.getFacilityReports(this.facility.programId)
                    .subscribe(reports => this.reports = reports.sort((a, b) => b.year - a.year));
                }
            });
        this.sharedService.emitChange(null);
    }

    isOneOf(baseValue: string, testValues: string[]): boolean {

        for (const value of testValues) {
            if (baseValue === value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create a new report. Either a copy of the previous report (if one exists) or a new report
     */
    createNewReport() {

        const modalWindow = this.modalService.open(BusyModalComponent, {
            backdrop: 'static',
            size: 'lg'
        });

        modalWindow.componentInstance.message = 'Please wait while we generate your new report.';

        const reportingYear = new Date().getFullYear() - 1;
        this.reportService.createReport(this.facility.programId, reportingYear)
        .subscribe(reportResp => {

            if (reportResp.status === 204) {

                // 204 No Content
                // no previous report, no FRS data
                modalWindow.dismiss();

                this._failedToCreateRef = this.modalService.open(
                    this._failedToCreateTemplate, {backdrop: 'static'});

            } else if (reportResp.status === 200) {

                // 200 OK
                // previous report was copied
                modalWindow.dismiss();

                this.reportCompleted(reportResp.body);

            } else if (reportResp.status === 202) {

                // 202 Accepted
                // pull more data from FRS
                modalWindow.componentInstance.message =
                    'Please wait: Searching for Facility Data in EPA\'s Facility Registry System to populate your Emissions Report';

                this.reportService.createReportFromFrs(this.facility.programId, reportingYear)
                .subscribe(newReport => {

                    modalWindow.dismiss();
                    this.reportCompleted(newReport);
                });
            }
        });

    }

    /**
     * Callback that is triggered after the new report is created
     */
    reportCompleted(newReport: EmissionsReport) {

        this.router.navigateByUrl(`/facility/${newReport.eisProgramId}/report/${newReport.id}/summary`);
    }

    onFailedToCreateCloseClick() {

        this._failedToCreateRef.dismiss();
    }

    deleteReport(reportId: number) {

        this.reportService.delete(reportId).subscribe(() => {
            this.route.data
            .subscribe((data: { facility: CdxFacility }) => {
                this.facility = data.facility;
                if (this.facility) {
                    this.reportService.getFacilityReports(this.facility.programId)
                    .subscribe(reports => {
                        this.reports = reports.sort((a, b) => b.year - a.year);
                    });
                }
            });
        });
    }

    openDeleteModal(eisProgramId: string, reportId: number, reportYear: number) {

        const modalRef = this.modalService.open(DeleteDialogComponent, { size: 'sm' });
        const modalMessage = `Are you sure you want to delete the ${reportYear} Emissions Report from this Facility
            (EIS Id ${eisProgramId})? This will also remove any Facility Information, Emission Units, Control Devices,
            and Release Point associated with this report.`;
        modalRef.componentInstance.message = modalMessage;
        modalRef.componentInstance.continue.subscribe(() => {
            this.deleteReport(reportId);
        });
    }
}
