import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmissionReviewModalComponent } from './submission-review-modal.component';

describe('SubmissionReviewModalComponent', () => {
  let component: SubmissionReviewModalComponent;
  let fixture: ComponentFixture<SubmissionReviewModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmissionReviewModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmissionReviewModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
