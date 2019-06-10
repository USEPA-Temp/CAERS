import { FacilitySite } from 'src/app/shared/models/facility-site';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { Component, Input } from '@angular/core';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { SharedService } from 'src/app/core/services/shared.service';


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent{
  @Input() facility: CdxFacility;
  facilitySite: FacilitySite;
  navItems: SideNavItem[];

  constructor(
      private emissionsUnitService: EmissionUnitService,
      sharedService: SharedService) {
    sharedService.changeEmitted$
    .subscribe(facilitySite => {
      this.facilitySite = facilitySite;
      this.getReportNav(this.facilitySite.id);
    });
  }

  getReportNav(facilityId: number) {
    this.emissionsUnitService.retrieveReportNavTree(facilityId)
      .subscribe(navItems => {
        console.log(JSON.stringify(navItems));
        this.navItems = navItems;
      });
  }
}
