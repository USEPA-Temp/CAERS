import { SharedModule } from '../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportsRoutingModule } from './reports-routing.module';
import { ReportsDashboardComponent } from './reports-dashboard/reports-dashboard.component';
import { ReportsComponent } from './reports/reports.component';
import { ReportListItemComponent } from './reports-dashboard/report-list-item/report-list-item.component';
import { ReportListButtonsComponent } from './reports-dashboard/report-list-item/report-list-buttons/report-list-buttons.component';

@NgModule({
  declarations: [
    ReportsDashboardComponent,
    ReportsComponent,
    ReportListItemComponent,
    ReportListButtonsComponent,
  ],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    SharedModule
  ]
})
export class ReportsModule { }
