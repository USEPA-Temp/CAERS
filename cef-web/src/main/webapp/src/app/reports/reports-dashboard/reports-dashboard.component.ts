import { EmissionsReport } from '../../model/emissions-report';
import { ReportService } from '../../services/report.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-reports-dashboard',
  templateUrl: './reports-dashboard.component.html',
  styleUrls: ['./reports-dashboard.component.css']
})
export class ReportsDashboardComponent implements OnInit {
  reports: EmissionsReport[];

  constructor(private reportService: ReportService) { }

  ngOnInit() {
    this.reportService.getFacilityReports('2774511')
    .subscribe(reports => this.reports = reports );
  }

}
