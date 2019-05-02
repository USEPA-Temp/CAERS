import { Injectable } from '@angular/core';
import { Router, ActivationStart, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RouteContextService {
  navStart: Observable<ActivationStart>;
  route: Observable<ActivatedRouteSnapshot>;

  constructor(private router: Router) {
    this.navStart = this.router.events.pipe(
      filter(evt => evt instanceof ActivationStart)
    ) as Observable<ActivationStart>;

    this.route = this.navStart.pipe(map(evt => evt.snapshot));
  }

}
