import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { BusyModalComponent } from 'src/app/shared/components/busy-modal/busy-modal.component';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FipsStateCode } from 'src/app/shared/models/fips-state-code';
import { FileDownloadService } from 'src/app/core/services/file-download.service';
import { ConfigPropertyService } from 'src/app/core/services/config-property.service';
import { OperatingStatus } from 'src/app/shared/enums/operating-status';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

@Component({
    selector: 'app-emissions-reporting-dashboard',
    templateUrl: './emissions-reporting-dashboard.component.html',
    styleUrls: ['./emissions-reporting-dashboard.component.scss']
})
export class EmissionsReportingDashboardComponent implements OnInit {
    facility: MasterFacilityRecord;
    facilitySite: FacilitySite;
    reports: EmissionsReport[];
    emissionsReport: EmissionsReport;
    operatingFacilityStatusValues: BaseCodeLookup[];
    excelExportEnabled = false;

    @ViewChild('FailedToCreateMessageBox', {static: true})
    _failedToCreateTemplate: TemplateRef<any>;

    _failedToCreateRef: NgbModalRef;

    constructor(
        private reportService: EmissionsReportingService,
        private fileDownloadService: FileDownloadService,
        private route: ActivatedRoute,
        private sharedService: SharedService,
        private modalService: NgbModal,
        public router: Router,
        public userContext: UserContextService,
        private facilitySiteService: FacilitySiteService,
        private propertyService: ConfigPropertyService,
        private lookupService: LookupService) { }


    ngOnInit() {

        this.route.data
            .subscribe((data: { facility: MasterFacilityRecord }) => {
                this.facility = data.facility;
                if (this.facility) {
                    this.reportService.getFacilityReports(this.facility.id)
                    .subscribe(reports => {this.reports = reports.sort((a, b) => b.year - a.year);
                    });
                }
            });
        this.sharedService.emitChange(null);
        this.facilitySite = new FacilitySite();

        this.propertyService.retrieveExcelExportEnabled()
        .subscribe(result => {
            this.excelExportEnabled = result;
        });

        this.lookupService.retrieveFacilityOperatingStatus()
        .subscribe(result => {
            this.operatingFacilityStatusValues = result;
        });

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
        this.reportService.createReportFromPreviousCopy(this.facility.id, reportingYear)
                .subscribe(reportResp => {
                    if (reportResp.status === 204) {
                        // 204 No Content
                        // no previous report

                        // this.copyFacilitySiteFromCdxModel();
                        this.lookupService.retrieveProgramSystemCodeByDescription(this.facility.programSystemCode?.description)
                        .subscribe(result => {
                            this.facilitySite.programSystemCode = result;
                            this.reportService.createReportFromScratch(this.facility.id, reportingYear)
                            .subscribe(reportResp => {
                                modalWindow.dismiss();
                                this.reportCompleted(reportResp.body);
                            });
                        })
                    } else if (reportResp.status === 200) {
                        // 200 OK
                        // previous report was copied

                        modalWindow.dismiss();

                        this.reportCompleted(reportResp.body);

                    }
                });
    }

    /**
     * Callback that is triggered after the new report is created
     */
    reportCompleted(newReport: EmissionsReport) {

        this.router.navigateByUrl(`/facility/${newReport.masterFacilityRecordId}/report/${newReport.id}/summary`);
    }

    onFailedToCreateCloseClick() {

            this._failedToCreateRef.dismiss();
        }

    deleteReport(reportId: number) {

        this.reportService.delete(reportId).subscribe(() => {
            this.route.data
            .subscribe((data: { facility: MasterFacilityRecord }) => {
                this.facility = data.facility;
                if (this.facility) {
                    this.reportService.getFacilityReports(this.facility.id)
                    .subscribe(reports => {
                        this.reports = reports.sort((a, b) => b.year - a.year);
                    });
                }
            });
        });
    }

    openDeleteModal(eisProgramId: string, reportId: number, reportYear: number) {

        const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
        const modalMessage = `Are you sure you want to delete the ${reportYear} Emissions Report from this Facility
            (EIS Id ${eisProgramId})? This will also remove any Facility Information, Emission Units, Control Devices,
            and Release Point associated with this report.`;
        modalRef.componentInstance.message = modalMessage;
        modalRef.componentInstance.continue.subscribe(() => {
            this.deleteReport(reportId);
        });
    }

    downloadExcelTemplate(report: EmissionsReport) {

        let reportFacility: FacilitySite;
        this.facilitySiteService.retrieveForReport(report.id)
        .subscribe(result => {
            reportFacility = result;
        });

        const modalWindow = this.modalService.open(BusyModalComponent, {
            backdrop: 'static',
            size: 'lg'
        });

        modalWindow.componentInstance.message = 'Please wait while we generate the Excel Template for this report. This may take a few minutes.';

        this.reportService.downloadExcelExport(report.id)
        .subscribe(file => {
            modalWindow.dismiss();

            // when being run on tomcat this call will return success with an empty body
            // instead of an error when the call times out
            if (file.size > 0) {
                this.fileDownloadService.downloadFile(file, `${reportFacility.altSiteIdentifier}-${this.facility.name}-${report.year}.xlsx`);
            } else {
                this.openDownloadFailure();
            }

        }, error => {
            modalWindow.dismiss();
            this.openDownloadFailure();
            console.log(error);
        });
    }

    openDownloadFailure() {

        const modalMessage = `The Excel Template for this report could not be generated due to a large number of users 
                              attempting to download Excel Templates in the system. Please try again in a few minutes.`;

        const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'lg' });
        modalRef.componentInstance.title = 'Template Download Failed';
        modalRef.componentInstance.message = modalMessage;
        modalRef.componentInstance.singleButton = true;
    }

    reopenReport(report) {
        const modalMessage = `Do you wish to reopen the ${report.year} report for
        ${this.facility.name}? This will reset the status of the report to "In progress" and you
        will need to resubmit the report to the S/L/T authority for review.`;
        const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
        modalRef.componentInstance.message = modalMessage;
        modalRef.componentInstance.continue.subscribe(() => {
            const ids = [report.id];
            this.resetReport(ids, report);
        });
    }

    resetReport(reportIds: number[], report) {
        this.reportService.resetReports(reportIds).subscribe(result => {
            this.router.navigate(['/facility/' + report.masterFacilityRecordId + '/report/' + report.id + '/summary']);
        });

    }

}
