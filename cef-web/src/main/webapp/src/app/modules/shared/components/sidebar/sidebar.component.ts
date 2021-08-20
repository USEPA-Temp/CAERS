/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { Component, Input, OnInit } from '@angular/core';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { ConfigPropertyService } from 'src/app/core/services/config-property.service';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  @Input() facility: MasterFacilityRecord;
  facilitySite: FacilitySite;
  emissionsNavItems: SideNavItem[];
  facilityNavItems: SideNavItem[];
  paginate: boolean;
  hideSideBar: boolean = false;

  bulkEntryEnabled: boolean;

  constructor(
      private emissionsUnitService: EmissionUnitService,
      private sharedService: SharedService,
      private propertyService: ConfigPropertyService) {
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
    })
  }



  ngOnInit() {
    this.sharedService.hideBoolChangeEmitted$.subscribe((result) => {
      this.hideSideBar = result;
    });

    this.propertyService.retrieveBulkEntryEnabled()
    .subscribe(result => {
      this.bulkEntryEnabled = result;
    });
  }

  getReportNav(facilityId: number) {
    this.emissionsUnitService.retrieveReportNavTree(facilityId)
      .subscribe(navItems => {
        navItems.forEach(navItem => {navItem.children = navItem.children.sort((a, b) => (a.description > b.description? 1 : -1)); })
        this.emissionsNavItems = navItems;
        if(this.emissionsNavItems.length < 25){
          this.paginate = false;
        }
        else{
          this.paginate = true;
        }
      });
  }

  getFacilityInventoryNav() {
    const items = [];
    items.push(new SideNavItem(null, 'Facility Information', BaseReportUrl.FACILITY_INFO, null));
    items.push(new SideNavItem(null, 'Emissions Units', BaseReportUrl.EMISSIONS_UNIT, null));
    items.push(new SideNavItem(null, 'Release Points', BaseReportUrl.RELEASE_POINT, null));
    items.push(new SideNavItem(null, 'Control Devices', BaseReportUrl.CONTROL_DEVICE, null));
    items.push(new SideNavItem(null, 'Control Paths', BaseReportUrl.CONTROL_PATH, null));
    this.facilityNavItems = items;

  }
}
