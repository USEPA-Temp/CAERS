import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Emission } from 'src/app/shared/models/emission';
import { FormBuilder } from '@angular/forms';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { Process } from 'src/app/shared/models/process';

@Component({
  selector: 'app-emission-details-modal',
  templateUrl: './emission-details-modal.component.html',
  styleUrls: ['./emission-details-modal.component.scss']
})
export class EmissionDetailsModalComponent implements OnInit {
  @Input() emission: Emission;
  @Input() reportingPeriod: ReportingPeriod;
  @Input() process: Process;
  @Input() editable = false;
  emissionForm = this.fb.group({
    pollutantName: [''],
    pollutantCode: [''],
    emissionsFactor: [''],
    emissionsCalcMethodCode: [''],
    totalEmissions: [''],
    emissionsUomCode: ['']
  });

  constructor(public activeModal: NgbActiveModal, private fb: FormBuilder) { }

  ngOnInit() {
    this.emissionForm.reset(this.emission);
    if (this.editable) {
      this.emissionForm.enable();
    } else {
      this.emissionForm.disable();
    }
  }

  onClose() {
    this.activeModal.close();
  }

  onReset() {
    // for demo purposes only
    this.editable = !this.editable;

    this.emissionForm.reset(this.emission);
    if (this.editable) {
      this.emissionForm.enable();
    } else {
      this.emissionForm.disable();
    }
  }

  onSubmit() {
    // TODO: rest call to save will be invoked here
    console.log(this.emissionForm.value);
    Object.assign(this.emission, this.emissionForm.value);
    this.activeModal.close();
  }

}
