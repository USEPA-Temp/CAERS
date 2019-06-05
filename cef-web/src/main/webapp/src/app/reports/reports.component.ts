import { EmissionsReport } from '../model/emissions-report';
import { Facility } from '../model/facility';
import { ReportService } from '../services/report.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {
  facility: Facility;
  report: EmissionsReport;

  constructor(private route: ActivatedRoute, private reportService: ReportService) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { facility: Facility }) => {
      this.facility = data.facility;

      this.reportService.getCurrentReport(this.facility.programId)
        .subscribe(report => this.report = report);
      });
  }

}
