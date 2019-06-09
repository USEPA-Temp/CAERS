import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { SharedService } from "src/app/core/services/shared.service";
import { EmissionsReportContextService } from "src/app/core/services/emissions-report-context.service";


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent{
  @Input() facility: CdxFacility;
  @Input() report: EmissionsReport;
  navItems: SideNavItem[];

  constructor(private emissionsUnitService: EmissionUnitService, sharedService: SharedService, private emissionsReportContextService:EmissionsReportContextService) {
      sharedService.changeEmitted$.subscribe(
              report => {
                  this.report=report;
                  this.getReportNav(this.report.facilitySite.id);
              });   
  }
  
  getReportNav(facilityId: number) {
    this.emissionsUnitService.retrieveReportNavTree(facilityId) //TODO pass the facility id
      .subscribe(navItems => {
        console.log(JSON.stringify(navItems));
        this.navItems = navItems;
      });
  }
}
