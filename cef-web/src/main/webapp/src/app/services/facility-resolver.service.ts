import { Facility } from '../model/facility';
import { FacilityContextService } from './facility-context.service';
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { take, mergeMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FacilityResolverService implements Resolve<Facility> {

  constructor(private facilityContext: FacilityContextService, private router: Router) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Facility> | Observable<never> {
    const id = route.paramMap.get('id');

    return this.facilityContext.getFacility(id).pipe(
      take(1),
      mergeMap(facility => {
        if (facility) {
          return of(facility);
        } else { // id not found
          this.router.navigate(['/facility']);
          console.log("facility not found");
          return EMPTY;
        }
      })
    );
  }
}
