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

declare const initCromerrWidget: any;

@Component({
  selector: 'app-report-summary',
  templateUrl: './report-summary.component.html',
  styleUrls: ['./report-summary.component.scss']
})
export class ReportSummaryComponent implements OnInit {
    facilitySite: FacilitySite;
    tableData: ReportSummary[];
    emissionsReportYear: number;
    showCertify: boolean = false;

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
        private emissionsReportingService: EmissionsReportingService) { }

    ngOnInit() {
        this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
            this.readOnlyMode = ReportStatus.APPROVED === data.facilitySite.emissionsReport.status;

            this.facilitySite = data.facilitySite;
            this.sharedService.emitChange(data.facilitySite);

            if (this.facilitySite.id) {
                this.emissionsReportYear = this.facilitySite.emissionsReport.year;
                this.userService.getCurrentUserNaasToken()
                .subscribe(userToken => {
                    this.userContextService.getUser().subscribe( user => {
                            if (user.role === 'Certifier' && this.facilitySite.emissionsReport.status !== 'SUBMITTED') {
                                this.showCertify = true;
                                initCromerrWidget(user.cdxUserId, user.userRoleId, userToken.baseServiceUrl,
                                    this.facilitySite.emissionsReport.id, this.facilitySite.eisProgramId, this.toastr);
                            }
                        });
                    }
                );
                this.reportService.retrieve(this.emissionsReportYear, this.facilitySite.id)
                .subscribe(report => {
                    this.tableData = report;
                });
            }
        });
        console.log("emissions report ",this.facilitySite.emissionsReport)
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

}
