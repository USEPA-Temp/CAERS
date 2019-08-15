import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReportSummary } from 'src/app/shared/models/report-summary';
import { ReportService } from 'src/app/core/services/report.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { UserService } from 'src/app/core/services/user.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { UserContextService } from 'src/app/core/services/user-context.service';

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

    constructor(
        private reportService: ReportService,
        private route: ActivatedRoute,
        private sharedService: SharedService,
        private userService: UserService,
        private userContextService: UserContextService) { }

    ngOnInit() {
        this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
            this.facilitySite = data.facilitySite;
            this.sharedService.emitChange(data.facilitySite);

            if (this.facilitySite.id) {
                this.emissionsReportYear = this.facilitySite.emissionsReport.year;
                this.userService.getCurrentUserNaasToken()
                .subscribe(userToken => {
                    this.userContextService.getUser().subscribe( user => {
                            if (user.role == 'Certifier' && this.facilitySite.emissionsReport.status!= 'SUBMITTED') {
                                this.showCertify = true;
                                initCromerrWidget(user.cdxUserId, user.userRoleId, userToken.baseServiceUrl,
                                    this.facilitySite.emissionsReport.id, this.facilitySite.eisProgramId);
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
    }
}