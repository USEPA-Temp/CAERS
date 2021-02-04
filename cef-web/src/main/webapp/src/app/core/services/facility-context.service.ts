import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { MasterFacilityRecordService } from 'src/app/core/services/master-facility-record.service';

@Injectable({
  providedIn: 'root'
})
export class FacilityContextService {
  private facility$: Observable<MasterFacilityRecord>;
  private programId: string;

  constructor(private mfrService: MasterFacilityRecordService) {
  }

  getFacility(programId: string): Observable<MasterFacilityRecord> {
    if (this.programId === programId) {
      return this.facility$;
    } else {
      this.programId = programId;
      this.fetchFacility();
      return this.facility$;
    }
  }

  private fetchFacility() {
    this.facility$ = this.mfrService.getRecordByEisProgramId(this.programId);
  }
}
