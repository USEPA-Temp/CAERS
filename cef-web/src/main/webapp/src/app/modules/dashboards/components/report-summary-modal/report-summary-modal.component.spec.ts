import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportSummaryModalComponent } from './report-summary-modal.component';

describe('ReportSummaryModalComponent', () => {
  let component: ReportSummaryModalComponent;
  let fixture: ComponentFixture<ReportSummaryModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportSummaryModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportSummaryModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
