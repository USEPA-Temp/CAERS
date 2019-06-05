import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-emissions-reporting-dashboard',
  templateUrl: './emissions-reporting-dashboard.component.html',
  styleUrls: ['./emissions-reporting-dashboard.component.scss']
})
export class EmissionsReportingDashboardComponent implements OnInit {
  facility: CdxFacility;
  reports: EmissionsReport[];

  constructor(private reportService: EmissionsReportingService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { facility: CdxFacility }) => {
      this.facility = data.facility;
      if (this.facility) {
          this.reportService.getFacilityReports(this.facility.programId)
            .subscribe(reports => this.reports = reports.sort((a, b) => b.year - a.year) );
      }
    });
  }

}
