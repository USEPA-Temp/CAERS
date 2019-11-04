import { FacilityContextService } from 'src/app/core/services/facility-context.service';
import { RouteContextService } from 'src/app/core/services/route-context.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { Component } from '@angular/core';
import { SessionTimeoutService } from 'src/app/core/services/session-timeout.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(
    private facilityContext: FacilityContextService,
    private userContext: UserContextService,
    private routeContext: RouteContextService,
    private timeoutService: SessionTimeoutService) {

      this.timeoutService.startSessionTimer();
  }

  keepAlive() {

    // Keep timeout modal from appearing while user is active
    this.timeoutService.refreshSessionTimer();
  }

}
