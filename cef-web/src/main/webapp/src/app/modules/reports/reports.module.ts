import { SharedModule } from 'src/app/shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportsRoutingModule } from './reports-routing.module';
import { ReportsDashboardComponent } from 'src/app/modules/reports/pages/reports-dashboard/reports-dashboard.component';
import { ReportsComponent } from 'src/app/modules/reports/pages/reports/reports.component';
import { ReportListItemComponent } from 'src/app/modules/reports/components/report-list-item/report-list-item.component';
import { ReportListButtonsComponent } from 'src/app/modules/reports/components/report-list-buttons/report-list-buttons.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EmissionUnitDashboardComponent } from 'src/app/modules/reports/pages/emission-unit-dashboard/emission-unit-dashboard.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { StepProgressComponent } from 'src/app/modules/reports/components/step-progress/step-progress.component';

import { EmissionUnitInfoComponent } from 'src/app/modules/reports/components/emission-unit-info/emission-unit-info.component';
import { EmissionProcessListComponent } from 'src/app/modules/reports/components/emission-process-list/emission-process-list.component';

@NgModule({
  declarations: [
    ReportsDashboardComponent,
    ReportsComponent,
    ReportListItemComponent,
    ReportListButtonsComponent,
    EmissionUnitDashboardComponent,
    StepProgressComponent,
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
