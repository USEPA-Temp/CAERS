import { EmissionsReport } from '../../model/emissions-report';
import { Facility } from '../../model/facility';
import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from 'src/app/model/side-nav-item';
import { EmissionUnitService } from "src/app/reports/services/emission-unit.service";


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  @Input() facility: Facility;
  @Input() report: EmissionsReport;
  navItems: SideNavItem[];

  constructor(private emissionsUnitService: EmissionUnitService) { }

  ngOnInit() {
      if (this.report) {
          this.getReportNav(this.report.id);
      }
  }

  getReportNav(facilityId: number) {
    this.emissionsUnitService.retrieveReportNavTree(9999991) //TODO pass the facility id
      .subscribe(navItems => {
        console.log(JSON.stringify(navItems));
        this.navItems = navItems;
      });
  }
}
