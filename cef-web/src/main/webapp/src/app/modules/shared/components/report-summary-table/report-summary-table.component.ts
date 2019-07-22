import { Component, OnInit, Input } from '@angular/core';
import { ReportSummary } from 'src/app/shared/models/report-summary';

@Component({
  selector: 'app-report-summary-table',
  templateUrl: './report-summary-table.component.html',
  styleUrls: ['./report-summary-table.component.scss']
})
export class ReportSummaryTableComponent implements OnInit {

    @Input() emissionsReportYear: number;
    @Input() reportSummaryList: ReportSummary[];

    constructor() { }

    ngOnInit() {
    }


    /***
     * Calculate the total number of tons for all pollutants
     */
    getPollutantTonsTotal(): number {
        let pollutantTonsTotal = 0;

        if (this.reportSummaryList) {
            this.reportSummaryList.forEach(reportSummary => {
                pollutantTonsTotal += reportSummary.emissionsTonsTotal;
            });
        }

        return pollutantTonsTotal;
    }


    /***
     * Calculate the total number of tons for all pollutants from the previous year
     */
    getPreviousPollutantTonsTotal(): number {
        let previousPollutantTonsTotal = 0;

        if (this.reportSummaryList) {
            this.reportSummaryList.forEach(reportSummary => {
                previousPollutantTonsTotal += reportSummary.previousYearTotal;
            });
        }

        return previousPollutantTonsTotal;
    }

}
