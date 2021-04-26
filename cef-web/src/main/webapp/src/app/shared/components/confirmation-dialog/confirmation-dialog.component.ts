import { Component, OnInit, Input, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent implements OnInit {

  @Input() title = 'Confirm';
  @Input() message: string;
  @Input() htmlMessage: string;
  @Input() cancelButtonText = 'Cancel';
  @Input() confirmButtonText = 'Confirm';
  @Input() singleButton = false;
  @Output() continue: EventEmitter<any> = new EventEmitter();

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

  onContinue() {
    this.continue.emit(null);
    this.activeModal.close(true);
  }

  onCancel() {
    this.activeModal.close(false);
  }

  onClose() {
    this.activeModal.close(false);
  }
}
