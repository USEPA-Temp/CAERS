import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import { ReportService } from 'src/app/core/services/report.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { SharedService } from 'src/app/core/services/shared.service';
import { ReportHistory } from 'src/app/shared/models/report-history';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';

@Component({
  selector: 'app-report-history',
  templateUrl: './report-history.component.html',
  styleUrls: ['./report-history.component.scss']
})
export class ReportHistoryComponent extends BaseSortableTable implements OnInit {
    facilitySite: FacilitySite;
    tableData: ReportHistory[];
    emissionsReportId: number;

    constructor(
        private reportService: ReportService,
        private route: ActivatedRoute,
        private sharedService: SharedService) {
            super();
        }

    ngOnInit() {
        this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
            this.facilitySite = data.facilitySite;
            this.sharedService.emitChange(data.facilitySite);
            if (this.facilitySite.id) {
                this.emissionsReportId = this.facilitySite.emissionsReport.id;
            }
        });

        this.reportService.retrieveHistory(this.emissionsReportId, this.facilitySite.id)
        .subscribe(report => {
            this.tableData = report;
        });
    }

}
