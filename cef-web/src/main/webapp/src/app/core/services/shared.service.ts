/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ValidationStatus } from 'src/app/shared/enums/validation-status.enum';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { ValidationResult } from 'src/app/shared/models/validation-result';
import { ControlPath } from 'src/app/shared/models/control-path';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  // Observable string sources
  private emitChangeSource = new Subject<any>();
  private emitSubmissionReviewChangeSource = new Subject<any>();
  private emitValidationResultChangeSource = new Subject<ValidationResult>();
  private emitControlsSource = new Subject<ControlPath[]>();
  private emitHideBoolSource = new Subject<any>();
  private emitReportIdSource = new Subject<any>();
  private emitProcessSccSource = new Subject<any>();
  private emitProcessOpStatus = new Subject<any>();
  private emitSltBannerSource = new Subject<any>();
  private emitAdminBannerSource = new Subject<any>();
  constructor(private toastr: ToastrService) { }

  // Observable string streams
  changeEmitted$ = this.emitChangeSource.asObservable();
  submissionReviewChangeEmitted$ = this.emitSubmissionReviewChangeSource.asObservable();
  validationResultChangeEmitted$ = this.emitValidationResultChangeSource.asObservable();
  controlsResultChangeEmitted$ = this.emitControlsSource.asObservable();
  hideBoolChangeEmitted$ = this.emitHideBoolSource.asObservable();
  reportIdChangeEmitted$ = this.emitReportIdSource.asObservable();
  processSccChangeEmitted$ = this.emitProcessSccSource.asObservable();
  processOpStatusChangeEmitted$ = this.emitProcessOpStatus.asObservable();
  sltBannerChangeEmitted$ = this.emitSltBannerSource.asObservable();
  adminBannerChangeEmitted$ = this.emitAdminBannerSource.asObservable();

  // Service message commands
  emitChange(change: any) {
    this.emitChangeSource.next(change);
  }

  emitHideBoolChange(change: any) {
    this.emitHideBoolSource.next(change);
  }

  emitSubmissionChange(change: any) {
    this.emitSubmissionReviewChangeSource.next(change);
  }

  emitValidationResultChange(change: ValidationResult) {
    this.emitValidationResultChangeSource.next(change);
  }

  emitControlsChange(change: ControlPath[]){
    this.emitControlsSource.next(change);
  }

  emitReportIdChange(change: any) {
    this.emitReportIdSource.next(change);
  }

  emitProcessSccChange(change: any) {
    this.emitProcessSccSource.next(change);
  }

  emitProcessOpStatusChange(change: any) {
    this.emitProcessOpStatus.next(change);
  }

  emitSltBannerChange(change: any) {
    this.emitSltBannerSource.next(change);
  }

  emitAdminSltBannerChange(change: any) {
    this.emitAdminBannerSource.next(change);
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
