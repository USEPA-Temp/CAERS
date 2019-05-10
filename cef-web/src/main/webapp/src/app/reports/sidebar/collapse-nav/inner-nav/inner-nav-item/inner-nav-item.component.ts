import { SideNavItem } from '../../../../model/side-nav-item';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-inner-nav-item',
  templateUrl: './inner-nav-item.component.html',
  styleUrls: ['./inner-nav-item.component.scss']
})
export class InnerNavItemComponent implements OnInit {
  @Input() navItem: SideNavItem;
  @Input() baseUrl: string;
  itemUrl: string;
  collapsed = true;
  targetId: string;

  constructor() { }

  ngOnInit() {

    this.itemUrl = `${this.baseUrl}${this.navItem.url}`;
    this.targetId = `${this.navItem.baseUrl}${this.navItem.id}children`;
  }

}
