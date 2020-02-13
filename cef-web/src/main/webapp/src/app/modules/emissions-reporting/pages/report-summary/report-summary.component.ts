import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { ReportSummary } from 'src/app/shared/models/report-summary';
import { ReportService } from 'src/app/core/services/report.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { ReportDownloadService } from 'src/app/core/services/report-download.service';
import { Subject } from 'rxjs';

declare const initCromerrWidget: any;

@Component({
  selector: 'app-report-summary',
  templateUrl: './report-summary.component.html',
  styleUrls: ['./report-summary.component.scss']
})
export class ReportSummaryComponent implements OnInit {
    facilitySite: FacilitySite;
    tableData: ReportSummary[];
    radiationData: ReportSummary[];
    emissionsReportYear: number;
    showCertify = false;
    cromerrLoaded = false;
    cromerrLoadedEmitter = new Subject<boolean>();

    readOnlyMode = true;

    constructor(
        private router: Router,
        private reportService: ReportService,
        private route: ActivatedRoute,
        private toastr: ToastrService,
        private sharedService: SharedService,
        private userService: UserService,
        private userContextService: UserContextService,
        private modalService: NgbModal,
        private emissionsReportingService: EmissionsReportingService,
        private reportDownloadService: ReportDownloadService) { }

    ngOnInit() {
        this.cromerrLoadedEmitter
        .subscribe(result => {
            this.cromerrLoaded = result;
        });

        this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
            this.readOnlyMode = ReportStatus.APPROVED === data.facilitySite.emissionsReport.status;

            this.facilitySite = data.facilitySite;
            this.sharedService.emitChange(data.facilitySite);

            if (this.facilitySite.id) {
                this.emissionsReportYear = this.facilitySite.emissionsReport.year;
                this.userService.getCurrentUserNaasToken()
                .subscribe(userToken => {
                    this.userContextService.getUser().subscribe( user => {
                            if (user.role === 'NEI Certifier' && this.facilitySite.emissionsReport.status !== 'SUBMITTED') {
                                this.showCertify = true;
                                initCromerrWidget(user.cdxUserId, user.userRoleId, userToken.baseServiceUrl,
                                    this.facilitySite.emissionsReport.id, this.facilitySite.eisProgramId, this.toastr, 
                                    this.cromerrLoadedEmitter);
                            }
                        });
                    }
                );
                this.reportService.retrieve(this.emissionsReportYear, this.facilitySite.id)
                    .subscribe(pollutants => {
                    // filter out radiation pollutants to show separately at the end of the table 
                    // (only radionucleides right now which is code 605)
                    this.tableData = pollutants.filter(pollutant => {
                        return pollutant.pollutantCode !== '605';
                    });

                    this.radiationData = pollutants.filter(pollutant => {
                        return pollutant.pollutantCode === '605';
                    });
                });
            }
        });
    }


    /**
     * validate the report
     */
    validateReport() {

        let reportId = this.facilitySite.emissionsReport.id;

        this.router.navigateByUrl(`/facility/${this.facilitySite.eisProgramId}/report/${reportId}/validation`);
    }

    reopenReport(){
        const modalMessage = `Do you wish to reopen the ${this.facilitySite.emissionsReport.year} report for 
        ${this.facilitySite.name}? This will reset the status of the report to "In progress" and you 
        will need to resubmit the report to the S/L/T authority for review. `;
        const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
        modalRef.componentInstance.message = modalMessage;
        modalRef.componentInstance.continue.subscribe(() => {
            let ids = [this.facilitySite.emissionsReport.id];
            this.resetReport(ids);
        });
    }

    resetReport(reportIds: number[]) {
        this.emissionsReportingService.resetReports(reportIds).subscribe(result => {
            location.reload();
        });
    }

    downloadReport(emissionsReportId: number, facilitySiteId: number) {
        this.reportService.retrieveReportDownloadDto(emissionsReportId, facilitySiteId).subscribe(reportDownloadDto => {
                this.reportDownloadService.downloadFile(reportDownloadDto, this.facilitySite.emissionsReport.id +'_'+
                this.facilitySite.emissionsReport.year +'_' + 'Emissions_Report');
        });
    }

}
