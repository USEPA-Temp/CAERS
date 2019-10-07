import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-base-confirmation-modal',
  templateUrl: './base-confirmation-modal.component.html',
  styleUrls: ['./base-confirmation-modal.component.scss']
})
export class BaseConfirmationModalComponent implements OnInit {

  @Input() title: string;
  @Input() message: string;
  @Input() cancelButtonText = 'Cancel';
  @Input() confirmButtonText = 'OK';

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

  onClose() {
    this.activeModal.dismiss();
  }

  onSubmit() {
    this.activeModal.close();
  }

}
