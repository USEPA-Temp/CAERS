import { Facility } from 'src/app/shared/models/facility';
import { FacilityService } from 'src/app/core/http/facility/facility.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacilityContextService {
  private facility$: Observable<Facility>;
  private programId: string;

  constructor(private facilityService: FacilityService) {
  }

  getFacility(programId: string): Observable<Facility> {
    if (this.programId === programId) {
      return this.facility$;
    } else {
      this.programId = programId;
      this.fetchFacility();
      return this.facility$;
    }
  }

  private fetchFacility() {
    this.facility$ = this.facilityService.getFacility(this.programId);
  }
}
