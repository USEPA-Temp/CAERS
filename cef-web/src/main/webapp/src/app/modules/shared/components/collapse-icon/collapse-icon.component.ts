import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-collapse-icon',
  templateUrl: './collapse-icon.component.html',
  styleUrls: ['./collapse-icon.component.scss']
})
export class CollapseIconComponent implements OnInit {
  @Input() collapsed: boolean;
  @Input() collapsedIcon = 'caret-right';
  @Input() expandedIcon = 'caret-down';

  constructor() { }

  ngOnInit() {
  }

}
