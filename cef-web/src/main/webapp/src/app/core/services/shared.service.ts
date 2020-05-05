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
  constructor(private toastr: ToastrService) { }

  // Observable string streams
  changeEmitted$ = this.emitChangeSource.asObservable();
  submissionReviewChangeEmitted$ = this.emitSubmissionReviewChangeSource.asObservable();
  validationResultChangeEmitted$ = this.emitValidationResultChangeSource.asObservable();
  controlsResultChangeEmitted$ = this.emitControlsSource.asObservable();
  hideBoolChangeEmitted$ = this.emitHideBoolSource.asObservable();

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
