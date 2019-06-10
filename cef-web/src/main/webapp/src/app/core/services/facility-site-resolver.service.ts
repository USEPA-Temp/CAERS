import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FacilitySiteContextService } from 'src/app/core/services/facility-site-context.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Resolve, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { take, mergeMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FacilitySiteResolverService implements Resolve<FacilitySite> {

  constructor(private facilitySiteContext: FacilitySiteContextService, private router: Router) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<FacilitySite> | Observable<never> {
    const programId = route.paramMap.get('facilityId');
    const reportId = +route.paramMap.get('reportId');

    return this.facilitySiteContext.getFacilitySite(programId, reportId).pipe(
      take(1),
      mergeMap(facilitySite => {
        if (facilitySite) {
          return of(facilitySite);
        } else { // id not found
          this.router.navigate(['/facility']);
          return EMPTY;
        }
      })
    );
  }
}
