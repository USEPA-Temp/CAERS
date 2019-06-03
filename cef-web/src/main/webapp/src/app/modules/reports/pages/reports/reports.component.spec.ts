import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportsComponent } from './reports.component';
import { ReportsDashboardComponent } from 'src/app/modules/reports/pages/reports-dashboard/reports-dashboard.component';
import { ReportListItemComponent } from 'src/app/modules/reports/components/report-list-item/report-list-item.component';
import { HttpClientModule } from '@angular/common/http';
import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { ReportListButtonsComponent } from 'src/app/modules/reports/components/report-list-buttons/report-list-buttons.component';
import { RouterTestingModule } from '@angular/router/testing';
import { SidebarComponent } from 'src/app/shared/components/sidebar/sidebar.component';
import { CollapseNavComponent } from 'src/app/shared/components/sidebar/collapse-nav/collapse-nav.component';
import { InnerNavComponent } from 'src/app/shared/components/sidebar/collapse-nav/inner-nav/inner-nav.component';
import { InnerNavItemComponent } from 'src/app/shared/components/sidebar/collapse-nav/inner-nav/inner-nav-item/inner-nav-item.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/shared/shared.module';


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
                      CollapseNavComponent,
                      InnerNavComponent,
                      InnerNavItemComponent],
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
