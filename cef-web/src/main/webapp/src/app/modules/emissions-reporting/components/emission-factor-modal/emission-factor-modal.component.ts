import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { EmissionFactor } from 'src/app/shared/models/emission-factor';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-emission-factor-modal',
  templateUrl: './emission-factor-modal.component.html',
  styleUrls: ['./emission-factor-modal.component.scss']
})
export class EmissionFactorModalComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: EmissionFactor[];
  efControl = new FormControl(null, Validators.required);

  constructor(public activeModal: NgbActiveModal) {
    super();
  }

  ngOnInit() {
  }

  onClose() {
    this.activeModal.dismiss();
  }

  onSubmit() {
    if (!this.efControl.valid) {
      this.efControl.markAsTouched();
    } else {
      this.activeModal.close(this.efControl.value);
    }
  }

}
