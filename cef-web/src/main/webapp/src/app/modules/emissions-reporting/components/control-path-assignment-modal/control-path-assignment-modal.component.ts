import { Component, OnInit, Input } from '@angular/core';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { Control } from 'src/app/shared/models/control';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ControlService } from 'src/app/core/services/control.service';
import { ControlPathService } from 'src/app/core/services/control-path.service';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-control-path-assignment-modal',
  templateUrl: './control-path-assignment-modal.component.html',
  styleUrls: ['./control-path-assignment-modal.component.scss']
})
export class ControlPathAssignmentModalComponent implements OnInit {
  @Input() controlPath: ControlPath;
  @Input() facilitySiteId: number;
  @Input() controlPathAssignments: ControlAssignment[];
  selectedControlPathAssignment: ControlAssignment;
  edit: boolean;
  controlPaths: ControlPath[];
  controls: Control[];
  controlSelected: string;
  controlPathChildSelected: string;

  controlPathAssignmentForm = this.fb.group({
    percentApportionment: ['', [Validators.required, Validators.max(100), Validators.pattern('[0-9]*')]],
    control: [''],
    controlPathChild: [''],
    sequenceNumber: ['', [Validators.required, Validators.pattern('[0-9]*')]]
  }, {validator: this.customValidationFunction});

  constructor(private controlService: ControlService,
              private controlPathService: ControlPathService,
              private fb: FormBuilder,
              public activeModal: NgbActiveModal) { }

  ngOnInit() {
      this.controlService.retrieveForFacilitySite(this.facilitySiteId)
      .subscribe(controls => {
        this.controls = controls;
        if (this.edit) {
          this.setControl();
        }
      });
      this.controlPathService.retrieveForFacilitySite(this.facilitySiteId)
      .subscribe(controlPaths => {
        this.controlPaths = controlPaths.filter(controlPath => controlPath.id !== this.controlPath.id);
        if (this.edit) {
          this.setControlPathChild();
        }
      });

      if (this.selectedControlPathAssignment) {
        this.controlPathAssignmentForm.reset(this.selectedControlPathAssignment);
      } else {
        this.selectedControlPathAssignment = new ControlAssignment();
      }
  }

  onClose() {
    this.activeModal.close('dontUpdate');
  }

  isValid() {
    return this.controlPathAssignmentForm.valid;
  }

  onSubmit() {
    if (!this.isValid()) {
        this.controlPathAssignmentForm.markAllAsTouched();
        this.controlPathAssignmentForm.controls.control.markAsDirty();
    } else {
        if (this.controlPathAssignmentForm.controls.controlPathChild.value) {
          this.controlPathAssignmentForm.controls.control.reset();
        }
        if (this.controlPathAssignmentForm.controls.control.value) {
          this.controlPathAssignmentForm.controls.controlPathChild.reset();
        }
        Object.assign(this.selectedControlPathAssignment, this.controlPathAssignmentForm.value);
        this.selectedControlPathAssignment.controlPath = this.controlPath;
        this.selectedControlPathAssignment.facilitySiteId = this.facilitySiteId;
        if (this.edit) {
          this.controlPathService.updateAssignmentForControlPath(this.selectedControlPathAssignment)
          .subscribe(() => {
            this.activeModal.close();
          });
        } else {
          this.controlPathService.createAssignmentForControlPath(this.selectedControlPathAssignment)
          .subscribe(() => {
            this.activeModal.close();
          });
        }
      }
  }

  // sets value of controlPathChild dropdown when editting an assignment
  setControlPathChild() {
    const controlPathChildField = this.controlPathAssignmentForm.get('controlPathChild').value;
    if (controlPathChildField) {
      for (const controlPath of this.controlPaths) {
        if (controlPath.id === controlPathChildField.id) {
          this.controlPathAssignmentForm.controls.controlPathChild.setValue(controlPath);
          break;
        }
      }
    }
  }

  // sets value of control dropdown when editting an assignment
  setControl() {
    const controlField = this.controlPathAssignmentForm.get('control').value;
    if (controlField) {
      for (const control of this.controls) {
        if (control.id === controlField.id) {
          this.controlPathAssignmentForm.controls.control.setValue(control);
          break;
        }
      }
    }
  }

  customValidationFunction(formGroup): any {
    const controlField = formGroup.controls.control.value;
    const controlPathChildField = formGroup.controls.controlPathChild.value;
    const sequenceNumber = formGroup.controls.sequenceNumber.value;
    const percentApportionment = formGroup.controls.percentApportionment.value;
    if (controlField && controlPathChildField) {
      return { controlAndPathSelected: true };
    }
    if (!controlField && !controlPathChildField) {
      return { controlAndPathNotSelected: true};
    }
    if ((controlField || controlPathChildField) && (sequenceNumber && percentApportionment)) {
      formGroup.status = 'VALID';
      return null;
    }
  }

}
