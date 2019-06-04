import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from 'src/app/model/side-nav-item';


@Component({
  selector: 'app-collapse-nav',
  templateUrl: './collapse-nav.component.html',
  styleUrls: ['./collapse-nav.component.scss']
})
export class CollapseNavComponent implements OnInit {
  @Input() collapsed = true;
  @Input() desc: string;
  @Input() targetId: string;
  @Input() navItems: SideNavItem[];

  constructor() { }

  ngOnInit() {
  }

}