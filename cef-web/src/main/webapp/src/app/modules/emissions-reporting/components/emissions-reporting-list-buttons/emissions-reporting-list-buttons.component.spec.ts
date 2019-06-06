import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsReportingListButtonsComponent } from './emissions-reporting-list-buttons.component';
import { HttpClientModule } from '@angular/common/http';
import { EmissionsReport } from 'src/app/shared/models/emissions-report';

describe('ReportListButtonsComponent', () => {
  let component: EmissionsReportingListButtonsComponent;
  let fixture: ComponentFixture<EmissionsReportingListButtonsComponent>;
  let report = new EmissionsReport();
  report.status = 'Failed';

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionsReportingListButtonsComponent ],
      imports: [HttpClientModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsReportingListButtonsComponent);
    component = fixture.componentInstance;
    component.report = report;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
