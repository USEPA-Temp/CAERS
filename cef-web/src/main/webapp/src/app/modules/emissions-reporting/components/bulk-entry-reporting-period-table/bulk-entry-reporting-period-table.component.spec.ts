import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BulkEntryReportingPeriodTableComponent } from './bulk-entry-reporting-period-table.component';

describe('BulkEntryReportingPeriodTableComponent', () => {
  let component: BulkEntryReportingPeriodTableComponent;
  let fixture: ComponentFixture<BulkEntryReportingPeriodTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BulkEntryReportingPeriodTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BulkEntryReportingPeriodTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
