import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFeedbackReportModalComponent } from './user-feedback-report-modal.component';

describe('ReportSummaryModalComponent', () => {
  let component: UserFeedbackReportModalComponent;
  let fixture: ComponentFixture<UserFeedbackReportModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserFeedbackReportModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFeedbackReportModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
