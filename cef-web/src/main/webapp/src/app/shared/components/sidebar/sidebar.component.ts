import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { EmissionsReportingService } from 'src/app/core/http/emissions-reporting/emissions-reporting.service';
import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  @Input() facility: CdxFacility;
  @Input() report: EmissionsReport;
  navItems: SideNavItem[];

  constructor(private reportService: EmissionsReportingService) { }

  ngOnInit() {
      if (this.report) {
          this.getReportNav(this.report.id);
      }
  }

  getReportNav(reportId: number) {
    this.reportService.getNavigation(reportId)
      .subscribe(navItems => {
        console.log(JSON.stringify(navItems));
        this.navItems = navItems;
      });
  }
}
