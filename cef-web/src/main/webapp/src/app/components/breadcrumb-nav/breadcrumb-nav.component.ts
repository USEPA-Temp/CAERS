import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-breadcrumb-nav',
  templateUrl: './breadcrumb-nav.component.html',
  styleUrls: ['./breadcrumb-nav.component.scss']
})
export class BreadcrumbNavComponent implements OnInit {
    //TODO: figure out how to pull breadcrumb path from route
  @Input() title;

  constructor() { }

  ngOnInit() {
  }

}
