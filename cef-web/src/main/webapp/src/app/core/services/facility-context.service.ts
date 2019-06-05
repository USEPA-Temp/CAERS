import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { CdxFacilityService } from 'src/app/core/services/cdx-facility.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacilityContextService {
  private facility$: Observable<CdxFacility>;
  private programId: string;

  constructor(private cdxFacilityService: CdxFacilityService) {
  }

  getFacility(programId: string): Observable<CdxFacility> {
    if (this.programId === programId) {
      return this.facility$;
    } else {
      this.programId = programId;
      this.fetchFacility();
      return this.facility$;
    }
  }

  private fetchFacility() {
    this.facility$ = this.cdxFacilityService.getFacility(this.programId);
  }
}
