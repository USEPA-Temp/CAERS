import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { FacilityContextService } from 'src/app/core/services/facility-context.service';
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { take, mergeMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FacilityResolverService implements Resolve<CdxFacility> {

  constructor(private facilityContext: FacilityContextService, private router: Router) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CdxFacility> | Observable<never> {
    const facilityId = route.paramMap.get('facilityId');

    return this.facilityContext.getFacility(facilityId).pipe(
      take(1),
      mergeMap(facility => {
        if (facility) {
          return of(facility);
        } else { // id not found
          this.router.navigate(['/facility']);
          return EMPTY;
        }
      })
    );
  }
}