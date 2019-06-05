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

import { EmissionUnitInfoComponent } from './emission-unit-dashboard/emission-unit-info/emission-unit-info.component';
import { EmissionProcessListComponent } from './emission-unit-dashboard/emission-process-list/emission-process-list.component';
al<<<<<<< Updated upstream
import { EmissionInventoryComponent } from './emission-inventory/emission-inventory.component';
import { ReleasePointTableComponent } from './emission-inventory/release-point-table/release-point-table.component';
import { EmissionsUnitTableComponent } from './emission-inventory/emissions-unit-table/emissions-unit-table.component';
import { EmissionsUnitToSideNavPipe } from './pipes/emissions-unit-to-side-nav.pipe';
import { FacilityInformationComponent } from './facility-information/facility-information.component';

@NgModule({
  declarations: [
    ReportsDashboardComponent,
    ReportsComponent,
    ReportListItemComponent,
    ReportListButtonsComponent,
    EmissionUnitDashboardComponent,
    StepProgressComponent,
    EmissionUnitInfoComponent,
    EmissionProcessListComponent,
    EmissionInventoryComponent,
    ReleasePointTableComponent,
    EmissionsUnitTableComponent,
    EmissionsUnitToSideNavPipe
    FacilityInformationComponent
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
=======
import { EmissionInventoryComponent } from './emission-inventory/emission-inventory.component';
import { ReleasePointTableComponent } from './emission-inventory/release-point-table/release-point-table.component';
import { EmissionsUnitTableComponent } from './emission-inventory/emissions-unit-table/emissions-unit-table.component';
import { EmissionsUnitToSideNavPipe } from './pipes/emissions-unit-to-side-nav.pipe';
import { FacilityInformationComponent } from './facility-information/facility-information.component';

@NgModule({
  declarations: [
    ReportsDashboardComponent,
    ReportsComponent,
    ReportListItemComponent,
    ReportListButtonsComponent,
    EmissionUnitDashboardComponent,
    StepProgressComponent,
    EmissionUnitInfoComponent,
    EmissionProcessListComponent,
    EmissionInventoryComponent,
    ReleasePointTableComponent,
    EmissionsUnitTableComponent,
    EmissionsUnitToSideNavPipe,
    FacilityInformationComponent
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
>>>>>>> Stashed changes
