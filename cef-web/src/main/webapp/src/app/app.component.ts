import { FacilityContextService } from 'src/app/core/services/facility-context.service';
import { RouteContextService } from 'src/app/core/services/route-context.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(
    private facilityContext: FacilityContextService,
    private userContext: UserContextService,
    private routeContext: RouteContextService
  ) { }

}
