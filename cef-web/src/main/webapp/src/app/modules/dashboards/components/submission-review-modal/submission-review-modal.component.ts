import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Validators, FormBuilder, ValidatorFn, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-submission-review-modal',
  templateUrl: './submission-review-modal.component.html',
  styleUrls: ['./submission-review-modal.component.scss']
})
export class SubmissionReviewModalComponent implements OnInit {

  @Input() title: string;
  @Input() message: string;
  @Input() cancelButtonText = 'Cancel';
  @Input() confirmButtonText = 'OK';

  reviewForm = this.fb.group({
    comments: ['', Validators.maxLength(2000)]
  });

  constructor(public activeModal: NgbActiveModal,
              private fb: FormBuilder) { }

  ngOnInit() {
  }

  onClose() {
    this.activeModal.dismiss();
  }

  onSubmit() {
    this.activeModal.close(this.reviewForm.get('comments').value);
  }

}