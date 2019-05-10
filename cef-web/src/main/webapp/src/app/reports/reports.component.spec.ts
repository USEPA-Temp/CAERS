import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportsComponent } from './reports.component';
import { ReportsDashboardComponent } from './reports-dashboard/reports-dashboard.component';
import { ReportListItemComponent } from "src/app/reports/reports-dashboard/report-list-item/report-list-item.component";
import { HttpClientModule } from "@angular/common/http";
import { EmissionsReport } from "src/app/model/emissions-report";
import { ReportListButtonsComponent } from "src/app/reports/reports-dashboard/report-list-item/report-list-buttons/report-list-buttons.component";
import { RouterTestingModule } from '@angular/router/testing';
import { SidebarComponent } from './sidebar/sidebar.component';
import { CollapseNavComponent } from './sidebar/collapse-nav/collapse-nav.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from "src/app/shared/shared.module";


describe('ReportsComponent', () => {
  let component: ReportsComponent;
  let fixture: ComponentFixture<ReportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportsComponent,
                      ReportsDashboardComponent,
                      ReportListItemComponent,
                      ReportListButtonsComponent,
                      SidebarComponent,
                      CollapseNavComponent],
      imports: [HttpClientModule, 
                RouterTestingModule,
                NgbModule,
                SharedModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
