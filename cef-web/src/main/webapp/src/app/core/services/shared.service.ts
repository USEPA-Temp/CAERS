import { Injectable } from '@angular/core';
import { Observable, of, EMPTY, Subject } from "rxjs";
import { ActivatedRoute } from '@angular/router';
import { ValidationStatus } from 'src/app/shared/enums/validation-status.enum';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  // Observable string sources
  private emitChangeSource = new Subject<any>();

  constructor(private toastr: ToastrService) { }

  // Observable string streams
  changeEmitted$ = this.emitChangeSource.asObservable();
  // Service message commands
  emitChange(change: any) {
    this.emitChangeSource.next(change);
  }

  updateReportStatusAndEmit(route: ActivatedRoute) {
    route.data.subscribe((data: { facilitySite: FacilitySite }) => {

      if (data.facilitySite.emissionsReport.validationStatus !== ValidationStatus.UNVALIDATED) {
        data.facilitySite.emissionsReport.validationStatus = ValidationStatus.UNVALIDATED;
        this.toastr.warning('You must run the Quality Checks on your report again since changes have been made to the report data.');
        this.emitChange(data.facilitySite);
      }
    });
  }

}
