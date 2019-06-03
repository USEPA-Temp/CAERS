import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmissionReviewDashboardComponent } from './submission-review-dashboard.component';

describe('SubmissionReviewDashboardComponent', () => {
  let component: SubmissionReviewDashboardComponent;
  let fixture: ComponentFixture<SubmissionReviewDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmissionReviewDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmissionReviewDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
