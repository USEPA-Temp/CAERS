import { Component, OnInit, Input } from '@angular/core';
import { ReportSummary } from 'src/app/shared/models/report-summary';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';

@Component({
  selector: 'app-report-summary-table',
  templateUrl: './report-summary-table.component.html',
  styleUrls: ['./report-summary-table.component.scss']
})
export class ReportSummaryTableComponent extends BaseSortableTable implements OnInit {

    @Input() emissionsReportYear: number;
    @Input() tableData: ReportSummary[];
    @Input() radiationData: ReportSummary[];

    constructor() {
        super();
     }

    ngOnInit() {
    }


    /***
     * Calculate the total number of tons for all pollutants
     */
    getPollutantTonsTotal(): number {
        let currentYearTonsTotal = 0;

        if (this.tableData) {
            this.tableData.forEach(reportSummary => {
                currentYearTonsTotal += reportSummary.emissionsTonsTotal;
            });
        }

        return currentYearTonsTotal;
    }


    /***
     * Calculate the total number of tons for all pollutants from the previous year
     */
    getPreviousPollutantTonsTotal(): number {
        let previousYearTonsTotal = 0;

        if (this.tableData) {
            this.tableData.forEach(reportSummary => {
                previousYearTonsTotal += reportSummary.previousYearTonsTotal;
            });
        }

        return previousYearTonsTotal;
    }

}
