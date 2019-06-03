import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { Facility } from 'src/app/shared/models/facility';
import { ReportService } from 'src/app/core/http/report/report.service';
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
      console.log(data.facility);
      this.facility = data.facility;

      this.reportService.getCurrentReport(this.facility.programId)
        .subscribe(report => this.report = report);
      });
  }

}
