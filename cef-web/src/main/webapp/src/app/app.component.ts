import { FacilityContextService } from './services/facility-context.service';
import { RouteContextService } from './services/route-context.service';
import { UserContextService } from './services/user-context.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(
    private facilityContext: FacilityContextService,
    private userContext: UserContextService,
    private routeContext: RouteContextService
  ) { }

}
