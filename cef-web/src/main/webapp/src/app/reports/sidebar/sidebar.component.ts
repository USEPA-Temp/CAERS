import { Facility } from '../../model/facility';
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
  navItems: SideNavItem[];

  constructor(private emissionUnitService: EmissionUnitService) { }

  ngOnInit() {

    this.emissionUnitService.retrieveFacilityNavTree(this.facility.programId)
      .subscribe(navItems => {
        console.log('nav tree', navItems);
        this.navItems = navItems;
      });
  }

}
