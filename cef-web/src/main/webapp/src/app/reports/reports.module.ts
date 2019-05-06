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

@NgModule({
  declarations: [
    ReportsDashboardComponent,
    ReportsComponent,
    ReportListItemComponent,
    ReportListButtonsComponent,
    SidebarComponent,
    CollapseNavComponent
  ],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    SharedModule,
    NgbModule
  ]
})
export class ReportsModule { }
