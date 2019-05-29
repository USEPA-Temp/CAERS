import { EmissionsReport } from '../../model/emissions-report';
import { Facility } from '../../model/facility';
import { ReportService } from '../../services/report.service';
import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from 'src/app/model/side-nav-item';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  @Input() facility: Facility;
  @Input() report: EmissionsReport;
  navItems: SideNavItem[];

  constructor(private reportService: ReportService) { }

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
