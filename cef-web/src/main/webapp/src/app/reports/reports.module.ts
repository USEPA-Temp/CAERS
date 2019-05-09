import { CollapseNavComponent } from './sidebar/collapse-nav/collapse-nav.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { SharedModule } from '../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportsRoutingModule } from './reports-routing.module';
import { ReportsDashboardComponent } from './reports-dashboard/reports-dashboard.component';
import { ReportsComponent } from './reports.component';
import { ReportListItemComponent } from './reports-dashboard/report-list-item/report-list-item.component';
import { ReportListButtonsComponent } from './reports-dashboard/report-list-item/report-list-buttons/report-list-buttons.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EmissionUnitDashboardComponent } from './emission-unit-dashboard/emission-unit-dashboard.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { StepProgressComponent } from './components/step-progress/step-progress.component';
import { InnerNavComponent } from './sidebar/collapse-nav/inner-nav/inner-nav.component';
import { EmissionUnitInfoComponent } from './emission-unit-dashboard/emission-unit-info/emission-unit-info.component';
import { EmissionProcessListComponent } from './emission-unit-dashboard/emission-process-list/emission-process-list.component';

@NgModule({
  declarations: [
    ReportsDashboardComponent,
    ReportsComponent,
    ReportListItemComponent,
    ReportListButtonsComponent,
    SidebarComponent,
    CollapseNavComponent,
    EmissionUnitDashboardComponent,
    StepProgressComponent,
    InnerNavComponent,
    EmissionUnitInfoComponent,
    EmissionProcessListComponent
  ],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    SharedModule,
    NgbModule,
    FontAwesomeModule
  ]
})
export class ReportsModule { }
