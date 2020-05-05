import { EmissionUnitDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emission-unit-dashboard/emission-unit-dashboard.component';
import { EmissionsReportingDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting-dashboard/emissions-reporting-dashboard.component';
import { EmissionsReportingComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting/emissions-reporting.component';
import { FacilityInformationComponent } from 'src/app/modules/emissions-reporting/pages/facility-information/facility-information.component';
import { ReleasePointDetailsComponent } from 'src/app/modules/emissions-reporting/pages/release-point-details/release-point-details.component';
import { EmissionsProcessDetailsComponent } from 'src/app/modules/emissions-reporting/pages/emissions-process-details/emissions-process-details.component';
import { ControlDeviceDetailsComponent } from 'src/app/modules/emissions-reporting/pages/control-device-details/control-device-details.component';
import { EmissionsUnitsSummaryComponent } from 'src/app/modules/emissions-reporting/pages/emissions-units-summary/emissions-units-summary.component';
import { ReleasePointsSummaryComponent } from 'src/app/modules/emissions-reporting/pages/release-points-summary/release-points-summary.component';
import { ControlDevicesSummaryComponent } from 'src/app/modules/emissions-reporting/pages/control-devices-summary/control-devices-summary.component';
import { ReportSummaryComponent } from 'src/app/modules/emissions-reporting/pages/report-summary/report-summary.component';
import { CreateEmissionsProcessComponent } from 'src/app/modules/emissions-reporting/pages/create-emissions-process/create-emissions-process.component';
import { CreateEmissionsUnitComponent } from 'src/app/modules/emissions-reporting/pages/create-emissions-unit/create-emissions-unit.component';
import { EditFacilityContactComponent } from 'src/app/modules/emissions-reporting/pages/edit-facility-contact/edit-facility-contact.component';
import { CreateReleasePointComponent } from './pages/create-release-point/create-release-point.component';
import { CreateControlDeviceComponent } from './pages/create-control-device/create-control-device.component';
import { ControlPathsSummaryComponent } from 'src/app/modules/emissions-reporting/pages/control-paths-summary/control-paths-summary.component';
import { ControlPathDetailsComponent } from 'src/app/modules/emissions-reporting/pages/control-path-details/control-path-details.component';
import { CreateControlPathComponent } from 'src/app/modules/emissions-reporting/pages/create-control-path/create-control-path.component';

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FacilitySiteResolverService } from 'src/app/core/services/facility-site-resolver.service';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { EmissionDetailsComponent } from 'src/app/modules/emissions-reporting/pages/emission-details/emission-details.component';
import {EmissionsReportValidationComponent} from './pages/emissions-report-validation/emissions-report-validation.component';
import { ReportHistoryComponent } from './pages/report-history/report-history.component';
import { EmissionsReportContainerComponent } from 'src/app/modules/emissions-reporting/pages/emissions-report-container/emissions-report-container.component';
import {ReportBulkUploadComponent} from './pages/report-bulk-upload/report-bulk-upload.component';
import { DataBulkEntryComponent } from 'src/app/modules/emissions-reporting/pages/data-bulk-entry/data-bulk-entry.component';

// TODO: this can probably be moved up a level so that it doesn't need to be in each child
const emissionsReportBreadcrumb = '&year Emissions Report';

const reportRoutes: Routes = [
  {
    path: '',
    component: EmissionsReportingComponent,
    data: {breadcrumb: 'Emissions Reports'},
    children: [{
        path: 'upload/:year',
        component: ReportBulkUploadComponent
    }, {
        path: ':reportId',
        component: EmissionsReportContainerComponent,
        resolve: {
          facilitySite: FacilitySiteResolverService
        },
        children: [
          {
            path: `${BaseReportUrl.FACILITY_INFO}`,
            component: FacilityInformationComponent,
            data: { title: 'Facility Information', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.FACILITY_CONTACT}`,
            component: EditFacilityContactComponent,
            data: { title: 'Facility Information', breadcrumb: emissionsReportBreadcrumb, create: 'true'}
          }, {
            path: `${BaseReportUrl.FACILITY_CONTACT}/:contactId`,
            component: EditFacilityContactComponent,
            data: { title: 'Facility Information', breadcrumb: emissionsReportBreadcrumb, create: 'false'}
          }, {
            path: `${BaseReportUrl.CONTROL_DEVICE}/create`,
            component: CreateControlDeviceComponent,
            data: { title: 'Control Device', breadcrumb: emissionsReportBreadcrumb, create: 'true'}
          }, {
            path: `${BaseReportUrl.CONTROL_DEVICE}/:controlId`,
            component: ControlDeviceDetailsComponent,
            data: { title: 'Control Device Details', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.CONTROL_DEVICE}`,
            component: ControlDevicesSummaryComponent,
            data: { title: 'Control Devices Summary', breadcrumb: emissionsReportBreadcrumb}
          }, {
          path: `${BaseReportUrl.CONTROL_PATH}/create`,
            component: CreateControlPathComponent,
            data: { title: 'Control Path', breadcrumb: emissionsReportBreadcrumb, create: 'true'}
          }, {
            path: `${BaseReportUrl.CONTROL_PATH}/:controlPathId`,
            component: ControlPathDetailsComponent,
            data: { title: 'Control Path Details', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.CONTROL_PATH}`,
            component: ControlPathsSummaryComponent,
            data: { title: 'Control Paths Summary', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.EMISSIONS_UNIT}/:unitId/process/create`,
            component: CreateEmissionsProcessComponent,
            data: { title: 'Emissions Unit', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.EMISSIONS_UNIT}/unit/create`,
            component: CreateEmissionsUnitComponent,
            data: { title: 'Emissions Unit', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.EMISSIONS_UNIT}/unit/create`,
            component: CreateEmissionsUnitComponent,
            data: { title: 'Emissions Unit', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.EMISSIONS_UNIT}/:unitId`,
            component: EmissionUnitDashboardComponent,
            data: { title: 'Emissions Unit', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.EMISSIONS_UNIT}`,
            component: EmissionsUnitsSummaryComponent,
            data: { title: 'Emissions Units Summary', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.EMISSIONS_PROCESS}/:processId`,
            component: EmissionsProcessDetailsComponent,
            data: { title: 'Emissions Process Details', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.REPORTING_PERIOD}/:periodId/${BaseReportUrl.EMISSION}`,
            component: EmissionDetailsComponent,
            data: { title: 'Emission Details', breadcrumb: emissionsReportBreadcrumb, create: 'true'}
          }, {
            path: `${BaseReportUrl.REPORTING_PERIOD}/:periodId/${BaseReportUrl.EMISSION}/:emissionId`,
            component: EmissionDetailsComponent,
            data: { title: 'Emission Details', breadcrumb: emissionsReportBreadcrumb, create: 'false'}
          }, {
            path: `${BaseReportUrl.RELEASE_POINT}/create`,
            component: CreateReleasePointComponent,
            data: { title: 'Release Point', breadcrumb: emissionsReportBreadcrumb, create: 'true'}
          }, {
            path: `${BaseReportUrl.RELEASE_POINT}/:releasePointId`,
            component: ReleasePointDetailsComponent,
            data: { title: 'Release Point', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.RELEASE_POINT}`,
            component: ReleasePointsSummaryComponent,
            data: { title: 'Release Points Summary', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.REPORT_SUMMARY}`,
            component: ReportSummaryComponent,
            data: { title: 'Report Summary', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.REPORT_HISTORY}`,
            component: ReportHistoryComponent,
            data: { title: 'Report History', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: `${BaseReportUrl.VALIDATION}`,
            component: EmissionsReportValidationComponent,
            data: { title: 'Report Validation', breadcrumb: 'Report Validation'}
          }, {
            path: `${BaseReportUrl.BULK_ENTRY}`,
            component: DataBulkEntryComponent,
            data: { title: 'Data Bulk Entry', breadcrumb: emissionsReportBreadcrumb}
          }, {
            path: '**',
            component: FacilityInformationComponent,
            data: { title: 'PLACEHOLDER', breadcrumb: emissionsReportBreadcrumb}
          }
        ]
      }, {
        path: '',
        component: EmissionsReportingDashboardComponent,
        data: { title: 'Emission Reports Dashboard'}
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(reportRoutes)],
  exports: [RouterModule]
})
export class ReportsRoutingModule { }
