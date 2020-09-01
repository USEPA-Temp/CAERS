import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UserFeedback } from 'src/app/shared/models/user-feedback';

@Component({
  selector: 'app-user-feedback-report-modal',
  templateUrl: './user-feedback-report-modal.component.html',
  styleUrls: ['./user-feedback-report-modal.component.scss']
})
export class UserFeedbackReportModalComponent implements OnInit {

    @Input() feedback: UserFeedback;

    constructor(public activeModal: NgbActiveModal) { }


    ngOnInit() {
    }

    onClose() {
      this.activeModal.close();
    }

}
