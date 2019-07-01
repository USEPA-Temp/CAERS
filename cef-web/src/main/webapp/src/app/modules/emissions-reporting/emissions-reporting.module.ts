import { SharedModule } from 'src/app/modules/shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

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
import { EmissionInventoryComponent } from 'src/app/modules/emissions-reporting/pages/emission-inventory/emission-inventory.component';
import { ReleasePointTableComponent } from 'src/app/modules/emissions-reporting/components/release-point-table/release-point-table.component';
import { EmissionsUnitTableComponent } from 'src/app/modules/emissions-reporting/components/emissions-unit-table/emissions-unit-table.component';
import { FacilityInformationComponent } from 'src/app/modules/emissions-reporting/pages/facility-information/facility-information.component';
import { EmissionsUnitToSideNavPipe } from 'src/app/shared/pipes/emissions-unit-to-side-nav.pipe';
import { ReleasePointDetailsComponent } from 'src/app/modules/emissions-reporting/pages/release-point-details/release-point-details.component';
import { EmissionsProcessTableComponent } from 'src/app/modules/emissions-reporting/components/emissions-process-table/emissions-process-table.component';
import { EmissionsProcessDetailsComponent } from 'src/app/modules/emissions-reporting/pages/emissions-process-details/emissions-process-details.component';
import { EmissionTableComponent } from 'src/app/modules/emissions-reporting/components/emission-table/emission-table.component';
import { ReleasePointApptTableComponent } from 'src/app/modules/emissions-reporting/components/release-point-appt-table/release-point-appt-table.component';
import { EmissionDetailsModalComponent } from 'src/app/modules/emissions-reporting/components/emission-details-modal/emission-details-modal.component';
import { ControlDeviceDetailsComponent } from 'src/app/modules/emissions-reporting/pages/control-device-details/control-device-details.component';
import { ControlAssignmentTableComponent } from 'src/app/modules/emissions-reporting/components/control-assignment-table/control-assignment-table.component';
import { ControlPollutantTableComponent } from 'src/app/modules/emissions-reporting/components/control-pollutant-table/control-pollutant-table.component';
import { InventoryControlTableComponent } from 'src/app/modules/emissions-reporting/components/inventory-control-table/inventory-control-table.component';
import { ControlDevicesTableComponent } from 'src/app/modules/emissions-reporting/components/control-devices-table/control-devices-table.component';
import { EmissionsUnitsSummaryComponent } from 'src/app/modules/emissions-reporting/pages/emissions-units-summary/emissions-units-summary.component';
import { ReleasePointsSummaryComponent } from 'src/app/modules/emissions-reporting/pages/release-points-summary/release-points-summary.component';
import { ControlDevicesSummaryComponent } from 'src/app/modules/emissions-reporting/pages/control-devices-summary/control-devices-summary.component';


@NgModule({
  declarations: [
    EmissionsReportingDashboardComponent,
    EmissionsReportingComponent,
    EmissionsReportingListItemComponent,
    EmissionsReportingListButtonsComponent,
    EmissionUnitDashboardComponent,
    StepProgressComponent,
    EmissionUnitInfoComponent,
    EmissionInventoryComponent,
    ReleasePointTableComponent,
    EmissionsUnitTableComponent,
    FacilityInformationComponent,
    EmissionsUnitToSideNavPipe,
    ReleasePointDetailsComponent,
    EmissionsProcessTableComponent,
    EmissionsProcessDetailsComponent,
    EmissionTableComponent,
    ReleasePointApptTableComponent,
    EmissionDetailsModalComponent,
    ControlDeviceDetailsComponent,
    ControlAssignmentTableComponent,
    ControlPollutantTableComponent,
    InventoryControlTableComponent,
    ControlDevicesTableComponent,
    EmissionsUnitsSummaryComponent,
    ReleasePointsSummaryComponent,
    ControlDevicesSummaryComponent

  ],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    ReactiveFormsModule,
    SharedModule,
    NgbModule,
    FontAwesomeModule
  ],
  entryComponents: [
    EmissionDetailsModalComponent
  ]
})
export class EmissionsReportingModule { }
