import { EmissionsReport } from '../../model/emissions-report';
import { Facility } from '../../model/facility';
import { ReportService } from '../../services/report.service';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reports-dashboard',
  templateUrl: './reports-dashboard.component.html',
  styleUrls: ['./reports-dashboard.component.css']
})
export class ReportsDashboardComponent implements OnInit {
  facility: Facility;
  reports: EmissionsReport[];

  constructor(private reportService: ReportService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { facility: Facility }) => {
      console.log(data.facility);
      this.facility = data.facility;
      this.reportService.getFacilityReports(this.facility.programId)
        .subscribe(reports => this.reports = reports );
    });
  }

}
