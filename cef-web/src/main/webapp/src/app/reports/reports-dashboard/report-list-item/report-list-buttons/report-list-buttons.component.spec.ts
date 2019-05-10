import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportListButtonsComponent } from './report-list-buttons.component';
import { HttpClientModule } from "@angular/common/http";
import { EmissionsReport } from "src/app/model/emissions-report";

describe('ReportListButtonsComponent', () => {
  let component: ReportListButtonsComponent;
  let fixture: ComponentFixture<ReportListButtonsComponent>;
  let report = new EmissionsReport();
  report.status="Failed";

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportListButtonsComponent ],
      imports: [HttpClientModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportListButtonsComponent);
    component = fixture.componentInstance;
    component.report=report;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
