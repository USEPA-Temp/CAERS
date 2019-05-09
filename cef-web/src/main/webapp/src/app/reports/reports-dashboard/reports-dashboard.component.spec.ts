import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportsDashboardComponent } from './reports-dashboard.component';
import { ReportListItemComponent } from "src/app/reports/reports-dashboard/report-list-item/report-list-item.component";
import { HttpClientModule } from "@angular/common/http";
import { EmissionsReport } from "src/app/model/emissions-report";
import { ReportListButtonsComponent } from "src/app/reports/reports-dashboard/report-list-item/report-list-buttons/report-list-buttons.component";
import { RouterTestingModule } from '@angular/router/testing';

describe('ReportsDashboardComponent', () => {
  let component: ReportsDashboardComponent;
  let fixture: ComponentFixture<ReportsDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportsDashboardComponent, 
                      ReportListItemComponent,
                      ReportListButtonsComponent],
      imports: [HttpClientModule, RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportsDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
