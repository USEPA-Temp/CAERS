import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReportSummary } from 'src/app/shared/models/report-summary';
import { ReportService } from 'src/app/core/services/report.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { UserService } from 'src/app/core/services/user.service';
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
        private userService: UserService,
        private userContextService: UserContextService) { }

    ngOnInit() {
        this.userService.getCurrentUserNaasToken()
        .subscribe(userToken =>{
            this.userContextService.getUser().subscribe( user => {
                    if (user.role == 'Certifier') {
                        this.showCertify = true;
                        initCromerrWidget(user.cdxUserId, user.userRoleId, userToken.baseServiceUrl);
                    }
                });
            }
        );

        this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
            this.facilitySite = data.facilitySite;

            if (this.facilitySite.id) {
                this.emissionsReportYear = this.facilitySite.emissionsReport.year;
                this.reportService.retrieve(this.emissionsReportYear, this.facilitySite.id)
                .subscribe(report => {
                    this.tableData = report;
                });
            }
        });
    }
}
