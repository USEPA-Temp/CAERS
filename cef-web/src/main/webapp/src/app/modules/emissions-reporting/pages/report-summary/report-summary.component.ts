import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReportSummary } from 'src/app/shared/models/report-summary';
import { ReportService } from 'src/app/core/services/report.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';


@Component({
  selector: 'app-report-summary',
  templateUrl: './report-summary.component.html',
  styleUrls: ['./report-summary.component.scss']
})
export class ReportSummaryComponent implements OnInit {
    facilitySite: FacilitySite;
    reportSummaryList: ReportSummary[];

    constructor(private reportService: ReportService, private route: ActivatedRoute) { }

    ngOnInit() {
        this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
            console.log(data.facilitySite);

            this.facilitySite = data.facilitySite;

            if (this.facilitySite.id) {
                this.reportService.retrieve(this.facilitySite.emissionsReport.year, this.facilitySite.id)
                .subscribe(report => {
                    console.log(report);
                    this.reportSummaryList = report;
                });
            }
        });
    }


    /***
     * Calculate the total number of tons for all pollutants
     */
    getPollutantTonsTotal(): number {
        let pollutantTonsTotal = 0;

        for(let reportSummary of this.reportSummaryList) {
            pollutantTonsTotal += reportSummary.emissionsTonsTotal;
        }

        return pollutantTonsTotal;
    }


    /***
     * Calculate the total number of tons for all pollutants from the previous year
     */
    getPreviousPollutantTonsTotal(): number {
        let previousPollutantTonsTotal = 0;

        for(let reportSummary of this.reportSummaryList) {
            previousPollutantTonsTotal += reportSummary.previousYearTotal;
        }

        return previousPollutantTonsTotal;
    }

}
