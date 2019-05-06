import { Facility } from '../model/facility';
import { FacilityService } from './facility.service';
import { RouteContextService } from './route-context.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FacilityContextService {
  facility: Facility;

  constructor(private facilityService: FacilityService, private routeContext: RouteContextService) {
    this.routeContext.route
      .subscribe(route  => {
        this.facilityService.getFacility(route.paramMap.get('id'))
          .subscribe(facility => this.facility =  facility);
      }
    );
   }
}
