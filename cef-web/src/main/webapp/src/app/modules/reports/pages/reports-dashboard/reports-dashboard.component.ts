import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { Facility } from 'src/app/shared/models/facility';
import { ReportService } from 'src/app/core/http/report/report.service';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reports-dashboard',
  templateUrl: './reports-dashboard.component.html',
  styleUrls: ['./reports-dashboard.component.scss']
})
export class ReportsDashboardComponent implements OnInit {
  facility: Facility;
  reports: EmissionsReport[];

  constructor(private reportService: ReportService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { facility: Facility }) => {
      this.facility = data.facility;
      if (this.facility) {
          this.reportService.getFacilityReports(this.facility.programId)
            .subscribe(reports => this.reports = reports.sort((a, b) => b.year - a.year) );
      }
    });
  }

}
