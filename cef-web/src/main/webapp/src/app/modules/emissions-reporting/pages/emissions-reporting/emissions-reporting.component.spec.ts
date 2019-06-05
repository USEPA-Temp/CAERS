import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsReportingComponent } from './emissions-reporting.component';
import { EmissionsReportingDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting-dashboard/emissions-reporting-dashboard.component';
import { EmissionsReportingListItemComponent } from 'src/app/modules/emissions-reporting/components/emissions-reporting-list-item/emissions-reporting-list-item.component';
import { HttpClientModule } from '@angular/common/http';
import { EmissionsReportingListButtonsComponent } from 'src/app/modules/emissions-reporting/components/emissions-reporting-list-buttons/emissions-reporting-list-buttons.component';
import { RouterTestingModule } from '@angular/router/testing';
import { SidebarComponent } from 'src/app/modules/shared/components/sidebar/sidebar.component';
import { CollapseNavComponent } from 'src/app/modules/shared/components/collapse-nav/collapse-nav.component';
import { SidebarInnerNavComponent } from 'src/app/modules/shared/components/sidebar-inner-nav/sidebar-inner-nav.component';
import { SidebarInnerNavItemComponent } from 'src/app/modules/shared/components/sidebar-inner-nav-item/sidebar-inner-nav-item.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/modules/shared/shared.module';


describe('EmissionsReportingComponent', () => {
  let component: EmissionsReportingComponent;
  let fixture: ComponentFixture<EmissionsReportingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionsReportingComponent,
                      EmissionsReportingDashboardComponent,
                      EmissionsReportingListItemComponent,
                      EmissionsReportingListButtonsComponent,
                      SidebarComponent,
                      CollapseNavComponent,
                      SidebarInnerNavComponent,
                      SidebarInnerNavItemComponent],
      imports: [HttpClientModule, 
                RouterTestingModule,
                NgbModule,
                SharedModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsReportingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
