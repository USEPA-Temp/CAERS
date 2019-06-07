import { Component, OnInit, Input } from '@angular/core';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';


@Component({
  selector: 'app-collapse-nav',
  templateUrl: './collapse-nav.component.html',
  styleUrls: ['./collapse-nav.component.scss']
})
export class CollapseNavComponent implements OnInit {

  @Input() collapsed = false;
  @Input() desc: string;
  @Input() targetId: string;
  @Input() linkUrl: string;
  @Input() navItems: SideNavItem[];

  constructor() { }

  ngOnInit() {
  }

}
