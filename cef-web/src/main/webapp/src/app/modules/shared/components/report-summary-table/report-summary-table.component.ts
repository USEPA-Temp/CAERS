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
        let precision = 0;

        if (this.tableData) {
            this.tableData.forEach(reportSummary => {
                currentYearTonsTotal += reportSummary.emissionsTonsTotal;

                if (this.getPrecision(reportSummary.emissionsTonsTotal) > precision) {
                    precision = this.getPrecision(reportSummary.emissionsTonsTotal);
                }
            });
        }

        return Math.round(currentYearTonsTotal*Math.pow(10, precision))/Math.pow(10, precision);
    }


    /***
     * Calculate the total number of tons for all pollutants from the previous year
     */
    getPreviousPollutantTonsTotal(): number {
        let previousYearTonsTotal = 0;
        let precision = 0;

        if (this.tableData) {
            this.tableData.forEach(reportSummary => {
                if (reportSummary.previousYear) {
                    previousYearTonsTotal += reportSummary.previousYearTonsTotal;

                    if (this.getPrecision(reportSummary.previousYearTonsTotal) > precision) {
                        precision = this.getPrecision(reportSummary.previousYearTonsTotal);
                    }
                }
            });

            return Math.round(previousYearTonsTotal*Math.pow(10, precision))/Math.pow(10, precision);
        }

    }


    getPrecision(value: number) {
        if (value.toString().includes('.')) {
            return value.toString().split('.')[1].length;
        } else {
            return 0;
        }
    }

}
