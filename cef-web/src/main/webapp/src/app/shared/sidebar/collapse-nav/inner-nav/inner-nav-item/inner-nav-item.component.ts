import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from "src/app/model/side-nav-item";

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
