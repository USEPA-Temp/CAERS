import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { MasterFacilityRecordService } from 'src/app/core/services/master-facility-record.service';

@Injectable({
  providedIn: 'root'
})
export class FacilityContextService {
  private facility$: Observable<MasterFacilityRecord>;
  private recordId: number;

  constructor(private mfrService: MasterFacilityRecordService) {
  }

  getFacility(recordId: number): Observable<MasterFacilityRecord> {
    if (this.recordId === recordId) {
      return this.facility$;
    } else {
      this.recordId = recordId;
      this.fetchFacility();
      return this.facility$;
    }
  }

  private fetchFacility() {
    this.facility$ = this.mfrService.getRecord(this.recordId);
  }
}
