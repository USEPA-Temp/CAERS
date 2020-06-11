import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-recalculate-emission-tons-modal',
  templateUrl: './recalculate-emission-tons-modal.component.html',
  styleUrls: ['./recalculate-emission-tons-modal.component.scss']
})
export class RecalculateEmissionTonsModalComponent implements OnInit {

  form = this.fb.group({
    reportId: [null, [Validators.required]]
  });

  constructor(public activeModal: NgbActiveModal, private fb: FormBuilder) { }

  ngOnInit() {
  }

  onClose() {
    this.activeModal.close();
  }

  onSubmit() {
    if (!this.form.valid) {
        this.form.markAllAsTouched();
    } else {
      this.activeModal.close(this.form.get('reportId').value);
    }
  }

}
