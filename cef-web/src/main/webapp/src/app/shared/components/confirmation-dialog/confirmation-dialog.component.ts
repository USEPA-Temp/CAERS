import { Component, OnInit, Input, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent implements OnInit {

  constructor(public activeModal: NgbActiveModal) { }
  @Input() message: string;
  @Output() continue: EventEmitter<any> = new EventEmitter();

  onContinue() {
    this.continue.emit(null);
    this.activeModal.close();
  }

  ngOnInit() {
  }

  onClose() {
    this.activeModal.close();
  }
}
