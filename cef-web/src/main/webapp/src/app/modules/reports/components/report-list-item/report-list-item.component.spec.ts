import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportListItemComponent } from './report-list-item.component';
import { HttpClientModule } from '@angular/common/http';
import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { ReportListButtonsComponent } from 'src/app/modules/reports/components/report-list-buttons/report-list-buttons.component';

describe('ReportListItemComponent', () => {
  let component: ReportListItemComponent;
  let fixture: ComponentFixture<ReportListItemComponent>;
  let report = new EmissionsReport();
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportListItemComponent , ReportListButtonsComponent],
      imports : [HttpClientModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportListItemComponent);
    component = fixture.componentInstance;
    component.report=report;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
