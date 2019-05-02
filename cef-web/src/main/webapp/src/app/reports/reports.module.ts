import { SharedModule } from '../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportsRoutingModule } from './reports-routing.module';
import { ReportsDashboardComponent } from './reports-dashboard/reports-dashboard.component';
import { ReportsComponent } from './reports/reports.component';

@NgModule({
  declarations: [
    ReportsDashboardComponent,
    ReportsComponent,
  ],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    SharedModule
  ]
})
export class ReportsModule { }
