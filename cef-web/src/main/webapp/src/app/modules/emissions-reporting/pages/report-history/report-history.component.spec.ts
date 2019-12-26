import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportHistoryComponent } from './report-history.component';

describe('ReportHistoryComponent', () => {
  let component: ReportHistoryComponent;
  let fixture: ComponentFixture<ReportHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
