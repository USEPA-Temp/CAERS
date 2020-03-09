import { Component, OnInit, Input} from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Process } from 'src/app/shared/models/process';
import { ReleasePointApportionment } from 'src/app/shared/models/release-point-apportionment';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ControlPath } from 'src/app/shared/models/control-path';

@Component({
  selector: 'app-release-point-apportionment-modal',
  templateUrl: './release-point-apportionment-modal.component.html',
  styleUrls: ['./release-point-apportionment-modal.component.scss']
})
export class ReleasePointApportionmentModalComponent implements OnInit {
  @Input() process: Process;
  @Input() releasePointApportionments: ReleasePointApportionment[];
  @Input() facilitySiteId: number;
  controlPaths: ControlPath[];
  edit: boolean;
  duplicateCheck = true;
  selectedReleasePoint: ReleasePointApportionment;
  releasePoints: ReleasePoint[];
  rpApptSelected: String;
  controlPathSelected: ControlPath;
  controlPathId: number;

  releasePointApptForm = this.fb.group({
    percent: ['', [Validators.required, Validators.min(1), Validators.max(100), Validators.pattern('[0-9]*')]],
    selectedReleasePointAppt: ['', Validators.required],
    controlPath: ['']
  });

  constructor(public activeModal: NgbActiveModal,
              private fb: FormBuilder,
              private releasePointService: ReleasePointService,
              private toastr: ToastrService) {}

  ngOnInit() {
    if (this.edit) {
      const releasePointApptControl = this.releasePointApptForm.get('selectedReleasePointAppt');
      releasePointApptControl.setValidators(null);

      // sets default value in dropdown
      for (let releasePt of this.releasePointApportionments) {
        if (releasePt.releasePointIdentifier === this.selectedReleasePoint.releasePointIdentifier) {
        this.rpApptSelected = releasePt.releasePointIdentifier;
        break;
        }
      }
      if(this.selectedReleasePoint.controlPath){
        for (let controlPath of this.controlPaths) {
          if (controlPath.id === this.selectedReleasePoint.controlPath.id) {
            this.controlPathSelected = controlPath;
            break;
          }
        }
      }

    }

    if (this.selectedReleasePoint) {
      this.releasePointApptForm.reset(this.selectedReleasePoint);
    } else {
      this.selectedReleasePoint = new ReleasePointApportionment();
    }

    this.releasePointService.retrieveForFacility(this.facilitySiteId)
    .subscribe(points => {
      this.releasePoints = points;
    });

  }

  isValid() {
    return this.releasePointApptForm.valid;
  }

  onClose() {
    this.activeModal.close('dontUpdate');
  }

  onSubmit(event) {
    event.target.disabled = true;
    if (!this.isValid()) {
        event.target.disabled = false;
        this.releasePointApptForm.markAllAsTouched();
      } else {
        // check for duplicate selection
        this.releasePointApportionments.forEach(apportionment => {
          if (this.releasePointApptForm.get('selectedReleasePointAppt').value) {
            if ((this.selectedReleasePoint.releasePointIdentifier !== this.releasePointApptForm.get('selectedReleasePointAppt').value.toString())
            && (apportionment.releasePointIdentifier === this.releasePointApptForm.get('selectedReleasePointAppt').value.toString())) {
              this.duplicateCheck = false;
              event.target.disabled = false;
              // tslint:disable-next-line: max-line-length
              this.toastr.error('', 'This Emissions Process already contains this Release Point Apportionment, duplicates are not allowed.', {positionClass: 'toast-top-right'});
            }
          }
        });
        if (this.duplicateCheck) {
        this.releasePoints.forEach(releasePoint => {
          if (!this.edit || this.releasePointApptForm.get('selectedReleasePointAppt').value) {
            if (releasePoint.releasePointIdentifier === this.releasePointApptForm.get('selectedReleasePointAppt').value.toString()) {
              this.selectedReleasePoint.releasePointId = releasePoint.id;
              this.selectedReleasePoint.emissionsProcessId = this.process.id;
              this.selectedReleasePoint.facilitySiteId = this.facilitySiteId;
              Object.assign(this.selectedReleasePoint, this.releasePointApptForm.value);
              if (!this.selectedReleasePoint.controlPath) {
                this.selectedReleasePoint.controlPath = null;
              }
            }
          } else {
              if (releasePoint.releasePointIdentifier === this.selectedReleasePoint.releasePointIdentifier) {
                this.selectedReleasePoint.releasePointId = releasePoint.id;
                this.selectedReleasePoint.emissionsProcessId = this.process.id;
                this.selectedReleasePoint.facilitySiteId = this.facilitySiteId;
                this.selectedReleasePoint.percent = this.releasePointApptForm.get('percent').value;
              }
          }
        });
        if (!this.edit) {
            this.releasePointService.createAppt(this.selectedReleasePoint)
            .subscribe(() => {
              this.activeModal.close();
            });
          } else {
            this.releasePointService.updateAppt(this.selectedReleasePoint)
            .subscribe(() => {
              this.activeModal.close();
            });
          }
        }
  }
    this.duplicateCheck = true;
  }

}
