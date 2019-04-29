import { UserContextService } from '../../services/user-context.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  //TODO: pass in a user to pull user info
  //TODO: figure out how to pull breadcrumb path from route
  @Input() title;

  constructor(public userContext: UserContextService) { }

  ngOnInit() {}
}
