import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from "src/app/model/side-nav-item";

@Component({
  selector: 'app-inner-nav',
  templateUrl: './inner-nav.component.html',
  styleUrls: ['./inner-nav.component.scss']
})
export class InnerNavComponent implements OnInit {
  @Input() navItems: SideNavItem[];
  @Input() baseUrl = '';

  constructor() { }

  ngOnInit() {
  }

}
