import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportSummaryTableComponent } from './report-summary-table.component';

describe('ReportSummaryTableComponent', () => {
  let component: ReportSummaryTableComponent;
  let fixture: ComponentFixture<ReportSummaryTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportSummaryTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportSummaryTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
