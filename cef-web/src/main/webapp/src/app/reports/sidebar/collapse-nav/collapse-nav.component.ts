import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-collapse-nav',
  templateUrl: './collapse-nav.component.html',
  styleUrls: ['./collapse-nav.component.scss']
})
export class CollapseNavComponent implements OnInit {
  @Input() collapsed = true;
  @Input() desc: string;
  @Input() targetId: string;

  constructor() { }

  ngOnInit() {
  }

}
