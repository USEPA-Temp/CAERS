import { SharedModule } from 'src/app/shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportsRoutingModule } from './emissions-reporting-routing.module';
import { EmissionsReportingDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting-dashboard/emissions-reporting-dashboard.component';
import { EmissionsReportingComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting/emissions-reporting.component';
import { EmissionsReportingListItemComponent } from 'src/app/modules/emissions-reporting/components/emissions-reporting-list-item/emissions-reporting-list-item.component';
import { EmissionsReportingListButtonsComponent } from 'src/app/modules/emissions-reporting/components/emissions-reporting-list-buttons/emissions-reporting-list-buttons.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EmissionUnitDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emission-unit-dashboard/emission-unit-dashboard.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { StepProgressComponent } from 'src/app/modules/emissions-reporting/components/step-progress/step-progress.component';

import { EmissionUnitInfoComponent } from 'src/app/modules/emissions-reporting/components/emission-unit-info/emission-unit-info.component';
import { EmissionProcessListComponent } from 'src/app/modules/emissions-reporting/components/emission-process-list/emission-process-list.component';
import { EmissionInventoryComponent } from 'src/app/modules/emissions-reporting/pages/emission-inventory/emission-inventory.component';
import { ReleasePointTableComponent } from 'src/app/modules/emissions-reporting/components/release-point-table/release-point-table.component';
import { EmissionsUnitTableComponent } from 'src/app/modules/emissions-reporting/components/emissions-unit-table/emissions-unit-table.component';
import { FacilityInformationComponent } from 'src/app/modules/emissions-reporting/pages/facility-information/facility-information.component';


@NgModule({
  declarations: [
    EmissionsReportingDashboardComponent,
    EmissionsReportingComponent,
    EmissionsReportingListItemComponent,
    EmissionsReportingListButtonsComponent,
    EmissionUnitDashboardComponent,
    StepProgressComponent,
    EmissionUnitInfoComponent,
    EmissionProcessListComponent,
    EmissionInventoryComponent,
    ReleasePointTableComponent,
    EmissionsUnitTableComponent,
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
