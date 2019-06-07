import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';

@Component({
  selector: 'app-sidebar-inner-nav-item',
  templateUrl: './sidebar-inner-nav-item.component.html',
  styleUrls: ['./sidebar-inner-nav-item.component.scss']
})
export class SidebarInnerNavItemComponent implements OnInit {
  @Input() navItem: SideNavItem;
  @Input() baseUrl: string;
  itemUrl: string;
  collapsed = false;
  targetId: string;

  constructor() { }

  ngOnInit() {

    this.itemUrl = `${this.baseUrl}${this.navItem.url}`;
    this.targetId = `${this.navItem.baseUrl}${this.navItem.id}children`;
  }

}
