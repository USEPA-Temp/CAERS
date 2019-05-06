import { FacilityContextService } from './services/facility-context.service';
import { RouteContextService } from './services/route-context.service';
import { UserContextService } from './services/user-context.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  showSidebar: boolean;

  constructor(
    private facilityContext: FacilityContextService, 
    private userContext: UserContextService, 
    private routeContext: RouteContextService
  ) { }

  ngOnInit(): void {
    this.routeContext.route.subscribe(snap => {
        this.showSidebar = snap.data.sidebar;
      });
  }
}
