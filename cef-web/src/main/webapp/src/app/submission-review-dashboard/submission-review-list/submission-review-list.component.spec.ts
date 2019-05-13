import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmissionReviewListComponent } from './submission-review-list.component';

describe('SubmissionReviewListComponent', () => {
  let component: SubmissionReviewListComponent;
  let fixture: ComponentFixture<SubmissionReviewListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmissionReviewListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmissionReviewListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
