import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FacilitySiteService } from './facility-site.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacilitySiteContextService {
  private facilitySite$: Observable<FacilitySite>;
  private programId: string;
  private reportId: number;

  constructor(private facilitySiteService: FacilitySiteService) { }

  getFacilitySite(programId: string, reportId: number): Observable<FacilitySite> {
    if (this.programId === programId && this.reportId === reportId) {
      return this.facilitySite$;
    } else {
      this.programId = programId;
      this.reportId = reportId;
      this.fetchFacilitySite();
      return this.facilitySite$;
    }
  }

  private fetchFacilitySite() {
    this.facilitySite$ = this.facilitySiteService.retrieveForReport(this.programId, this.reportId);
  }

}
