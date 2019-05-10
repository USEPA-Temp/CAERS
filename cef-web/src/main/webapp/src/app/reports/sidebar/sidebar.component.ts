import { EmissionsReport } from '../../model/emissions-report';
import { Facility } from '../../model/facility';
import { ReportService } from '../../services/report.service';
import { SideNavItem } from '../model/side-nav-item';
import { EmissionUnitService } from '../services/emission-unit.service';
import { Component, OnInit, Input } from '@angular/core';

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

    this.getNav();
  }

  getNav() {

    this.reportService.getNavigation(this.report.id)
      .subscribe(navItems => {
        console.log(JSON.stringify(navItems));
        this.navItems = navItems;
      });
  }

}
