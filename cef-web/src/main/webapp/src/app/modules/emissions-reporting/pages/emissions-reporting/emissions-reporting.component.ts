import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-emissions-reporting',
  templateUrl: './emissions-reporting.component.html',
  styleUrls: ['./emissions-reporting.component.scss']
})
export class EmissionsReportingComponent implements OnInit {
  facility: CdxFacility;
  report: EmissionsReport;

  constructor(private route: ActivatedRoute, private reportService: EmissionsReportingService) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { facility: CdxFacility }) => {
      this.facility = data.facility;

      this.reportService.getCurrentReport(this.facility.programId)
        .subscribe(report => this.report = report);
      });
  }


}
