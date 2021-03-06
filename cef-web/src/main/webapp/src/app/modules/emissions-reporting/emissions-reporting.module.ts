/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule} from '@angular/forms';

import { ReportsRoutingModule } from './emissions-reporting-routing.module';
import { EmissionsReportingDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting-dashboard/emissions-reporting-dashboard.component';
import { EmissionsReportingComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting/emissions-reporting.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EmissionUnitDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emission-unit-dashboard/emission-unit-dashboard.component';
import { FaConfig, FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import { StepProgressComponent } from 'src/app/modules/emissions-reporting/components/step-progress/step-progress.component';


import { EmissionUnitInfoComponent } from 'src/app/modules/emissions-reporting/components/emission-unit-info/emission-unit-info.component';
import { ReleasePointTableComponent } from 'src/app/modules/emissions-reporting/components/release-point-table/release-point-table.component';
import { EmissionsUnitTableComponent } from 'src/app/modules/emissions-reporting/components/emissions-unit-table/emissions-unit-table.component';
import { FacilityInformationComponent } from 'src/app/modules/emissions-reporting/pages/facility-information/facility-information.component';
import { EmissionsUnitToSideNavPipe } from 'src/app/shared/pipes/emissions-unit-to-side-nav.pipe';
import { ReleasePointDetailsComponent } from 'src/app/modules/emissions-reporting/pages/release-point-details/release-point-details.component';
import { EmissionsProcessTableComponent } from 'src/app/modules/emissions-reporting/components/emissions-process-table/emissions-process-table.component';
import { EmissionsProcessDetailsComponent } from 'src/app/modules/emissions-reporting/pages/emissions-process-details/emissions-process-details.component';
import { EmissionTableComponent } from 'src/app/modules/emissions-reporting/components/emission-table/emission-table.component';
import { ReleasePointApptTableComponent } from 'src/app/modules/emissions-reporting/components/release-point-appt-table/release-point-appt-table.component';
import { ControlDeviceDetailsComponent } from 'src/app/modules/emissions-reporting/pages/control-device-details/control-device-details.component';
import { ControlAssignmentTableComponent } from 'src/app/modules/emissions-reporting/components/control-assignment-table/control-assignment-table.component';
import { ControlPollutantTableComponent } from 'src/app/modules/emissions-reporting/components/control-pollutant-table/control-pollutant-table.component';
import { ControlPathPollutantTableComponent } from 'src/app/modules/emissions-reporting/components/control-path-pollutant-table/control-path-pollutant-table.component';
import { InventoryControlTableComponent } from 'src/app/modules/emissions-reporting/components/inventory-control-table/inventory-control-table.component';
import { ControlPathPanelComponent } from 'src/app/modules/emissions-reporting/components/control-path-panel/control-path-panel.component';
import { EmissionsUnitsSummaryComponent } from 'src/app/modules/emissions-reporting/pages/emissions-units-summary/emissions-units-summary.component';
import { ReleasePointsSummaryComponent } from 'src/app/modules/emissions-reporting/pages/release-points-summary/release-points-summary.component';
import { ControlDevicesSummaryComponent } from 'src/app/modules/emissions-reporting/pages/control-devices-summary/control-devices-summary.component';
import { ReportSummaryComponent } from 'src/app/modules/emissions-reporting/pages/report-summary/report-summary.component';
import { CreateEmissionsProcessComponent } from 'src/app/modules/emissions-reporting/pages/create-emissions-process/create-emissions-process.component';
import { EditProcessInfoPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-info-panel/edit-process-info-panel.component';
import { EditProcessOperatingDetailPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-operating-detail-panel/edit-process-operating-detail-panel.component';
import { EditProcessReportingPeriodPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-process-reporting-period-panel/edit-process-reporting-period-panel.component';
import { EmissionDetailsComponent } from './pages/emission-details/emission-details.component';
import { EmissionFactorModalComponent } from './components/emission-factor-modal/emission-factor-modal.component';
import { SccSearchModalComponent } from './components/scc-search-modal/scc-search-modal.component';
import { ControlPathTableComponent } from './components/control-path-table/control-path-table.component';
import { EmissionsReportValidationComponent } from './pages/emissions-report-validation/emissions-report-validation.component';
import { EditFacilityContactComponent } from './pages/edit-facility-contact/edit-facility-contact.component';
import { EditEmissionUnitInfoPanelComponent } from './components/edit-emission-unit-info-panel/edit-emission-unit-info-panel.component';
import { CreateEmissionsUnitComponent } from './pages/create-emissions-unit/create-emissions-unit.component';
import { EditReleasePointPanelComponent } from './components/edit-release-point-panel/edit-release-point-panel.component';
import { CreateReleasePointComponent } from './pages/create-release-point/create-release-point.component';
import { ReleasePointApportionmentModalComponent } from './components/release-point-apportionment-modal/release-point-apportionment-modal.component';
import { EditControlDeviceInfoPanelComponent } from './components/edit-control-device-info-panel/edit-control-device-info-panel.component';
import { CreateControlDeviceComponent } from './pages/create-control-device/create-control-device.component';
import { EditFacilitySiteInfoPanelComponent } from './components/edit-facility-site-info-panel/edit-facility-site-info-panel.component';
import { FacilityNaicsTableComponent } from './components/facility-naics-table/facility-naics-table.component';
import { FacilityNaicsModalComponent } from './components/facility-naics-modal/facility-naics-modal.component';
import { ReportHistoryComponent } from './pages/report-history/report-history.component';
import { ControlPollutantModalComponent } from './components/control-pollutant-modal/control-pollutant-modal.component';
import { EmissionsReportContainerComponent } from './pages/emissions-report-container/emissions-report-container.component';
import { ValidationReminderComponent } from './components/validation-reminder/validation-reminder.component';
import { ControlPathsSummaryComponent } from './pages/control-paths-summary/control-paths-summary.component';
import { ControlPathsTableComponent } from './components/control-paths-table/control-paths-table.component';
import { ControlPathDetailsComponent } from './pages/control-path-details/control-path-details.component';
import { EditControlPathInfoPanelComponent } from './components/edit-control-path-info-panel/edit-control-path-info-panel.component';
import { CreateControlPathComponent } from './pages/create-control-path/create-control-path.component';
import { ControlPathAssignmentTableComponent } from './components/control-path-assignment-table/control-path-assignment-table.component';
import { ControlPathAssignmentModalComponent } from './components/control-path-assignment-modal/control-path-assignment-modal.component';
import { ReportBulkUploadComponent } from './pages/report-bulk-upload/report-bulk-upload.component';
import { UserFeedbackComponent } from './pages/user-feedback/user-feedback.component';
import { DataBulkEntryComponent } from './pages/data-bulk-entry/data-bulk-entry.component';
import { BulkEntryReportingPeriodTableComponent } from './components/bulk-entry-reporting-period-table/bulk-entry-reporting-period-table.component';
import { BulkEntryEmissionsTableComponent } from './components/bulk-entry-emissions-table/bulk-entry-emissions-table.component';
import {faBan, faPlus, faQuestionCircle, fas, faUserCircle} from "@fortawesome/free-solid-svg-icons";
import { ThresholdScreeningGadnrModalComponent } from './components/threshold-screening-gadnr-modal/threshold-screening-gadnr-modal.component';

@NgModule({
  declarations: [
    EmissionsReportingDashboardComponent,
    EmissionsReportingComponent,
    EmissionUnitDashboardComponent,
    StepProgressComponent,
    EmissionUnitInfoComponent,
    ReleasePointTableComponent,
    EmissionsUnitTableComponent,
    FacilityInformationComponent,
    EmissionsUnitToSideNavPipe,
    ReleasePointDetailsComponent,
    EmissionsProcessTableComponent,
    EmissionsProcessDetailsComponent,
    EmissionTableComponent,
    ReleasePointApptTableComponent,
    ControlDeviceDetailsComponent,
    ControlAssignmentTableComponent,
    ControlPollutantTableComponent,
    ControlPathPollutantTableComponent,
    InventoryControlTableComponent,
    ControlPathPanelComponent,
    EmissionsUnitsSummaryComponent,
    ReleasePointsSummaryComponent,
    ControlDevicesSummaryComponent,
    ReportSummaryComponent,
    CreateEmissionsProcessComponent,
    EditProcessInfoPanelComponent,
    EditProcessOperatingDetailPanelComponent,
    EditProcessReportingPeriodPanelComponent,
    EmissionDetailsComponent,
    EmissionFactorModalComponent,
    SccSearchModalComponent,
    ControlPathTableComponent,
    EmissionsReportValidationComponent,
    EditFacilityContactComponent,
    EditEmissionUnitInfoPanelComponent,
    CreateEmissionsUnitComponent,
    EditReleasePointPanelComponent,
    CreateReleasePointComponent,
    ReleasePointApportionmentModalComponent,
    EditControlDeviceInfoPanelComponent,
    CreateControlDeviceComponent,
    EditFacilitySiteInfoPanelComponent,
    FacilityNaicsTableComponent,
    FacilityNaicsModalComponent,
    ReportHistoryComponent,
    ControlPollutantModalComponent,
    EmissionsReportContainerComponent,
    ValidationReminderComponent,
    ControlPathsSummaryComponent,
    ControlPathsTableComponent,
    ControlPathDetailsComponent,
    EditControlPathInfoPanelComponent,
    CreateControlPathComponent,
    ControlPathAssignmentTableComponent,
    ControlPathAssignmentModalComponent,
    ReportBulkUploadComponent,
    UserFeedbackComponent,
    DataBulkEntryComponent,
    BulkEntryReportingPeriodTableComponent,
    BulkEntryEmissionsTableComponent,
    ThresholdScreeningGadnrModalComponent
  ],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    ReactiveFormsModule,
    SharedModule,
    NgbModule,
    FontAwesomeModule,
    FormsModule
  ],
  entryComponents: [
    EmissionFactorModalComponent,
    SccSearchModalComponent,
    ReleasePointApportionmentModalComponent,
    FacilityNaicsModalComponent,
    ControlPollutantModalComponent,
    ControlPathAssignmentModalComponent
  ]
})
export class EmissionsReportingModule {
    constructor(config: FaConfig, library: FaIconLibrary) {
        config.fallbackIcon = faBan;
        library.addIconPacks(fas);
      library.addIcons(faUserCircle, faQuestionCircle, faPlus);
    }
}
