import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';

@Component({
  selector: 'app-sidebar-inner-nav',
  templateUrl: './sidebar-inner-nav.component.html',
  styleUrls: ['./sidebar-inner-nav.component.scss']
})
export class SidebarInnerNavComponent implements OnInit {
  @Input() navItems: SideNavItem[];
  @Input() baseUrl = '';

  constructor() { }

  ngOnInit() {
  }

}
