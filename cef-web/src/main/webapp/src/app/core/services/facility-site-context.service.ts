import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FacilitySiteService } from './facility-site.service';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacilitySiteContextService {
  private facilitySite$: Observable<FacilitySite>;
  private programId: string;
  private reportId: number;
  
  // Observable string sources
  private emitChangeSource = new Subject<FacilitySite>();
  // Observable string streams
  changeEmitted$ = this.emitChangeSource.asObservable();

  constructor(private facilitySiteService: FacilitySiteService) { }

  getFacilitySite(programId: string, reportId: number): Observable<FacilitySite> {
    if (this.programId === programId && this.reportId === reportId) {
      return this.facilitySite$;
    } else {
      this.programId = programId;
      this.reportId = reportId;
      this.fetchFacilitySite();
//      this.facilitySite$.subscribe(facilitySite => this.emitChange(facilitySite));
      return this.facilitySite$;
    }
  }

  private fetchFacilitySite() {
    this.facilitySite$ = this.facilitySiteService.retrieveForReport(this.programId, this.reportId);
  }
  // Service message commands
  private emitChange(change: FacilitySite) {
      this.emitChangeSource.next(change);
  }

}
