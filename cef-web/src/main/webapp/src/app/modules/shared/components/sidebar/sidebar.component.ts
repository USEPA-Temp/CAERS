import { FacilitySite } from 'src/app/shared/models/facility-site';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { Component, Input } from '@angular/core';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent{
  @Input() facility: CdxFacility;
  facilitySite: FacilitySite;
  emissionsNavItems: SideNavItem[];
  facilityNavItems: SideNavItem[];

  constructor(
      private emissionsUnitService: EmissionUnitService,
      sharedService: SharedService) {
    sharedService.changeEmitted$
    .subscribe(facilitySite => {
      if (facilitySite != null) {
          this.facilitySite = facilitySite;
          this.getReportNav(this.facilitySite.id);
          this.getFacilityInventoryNav();
      } else {
          this.emissionsNavItems = null;
          this.facilityNavItems = null;
          this.facilitySite = null;
      }
    });
  }

  getReportNav(facilityId: number) {
    this.emissionsUnitService.retrieveReportNavTree(facilityId)
      .subscribe(navItems => {
        console.log(JSON.stringify(navItems));
        this.emissionsNavItems = navItems;
      });
  }

  getFacilityInventoryNav() {
    const items = [];
    items.push(new SideNavItem(null, 'Facility Information', BaseReportUrl.FACILITY_INFO, null));
    items.push(new SideNavItem(null, 'Emissions Units', BaseReportUrl.EMISSIONS_UNIT, null));
    items.push(new SideNavItem(null, 'Release Points', BaseReportUrl.RELEASE_POINT, null));
    items.push(new SideNavItem(null, 'Control Devices', BaseReportUrl.CONTROL_DEVICE, null));
    this.facilityNavItems = items;

  }
}
