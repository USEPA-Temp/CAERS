/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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
