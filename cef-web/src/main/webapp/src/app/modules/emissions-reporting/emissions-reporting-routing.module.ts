import { EmissionUnitDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emission-unit-dashboard/emission-unit-dashboard.component';
import { EmissionsReportingDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting-dashboard/emissions-reporting-dashboard.component';
import { EmissionsReportingComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting/emissions-reporting.component';
import { EmissionInventoryComponent } from 'src/app/modules/emissions-reporting/pages/emission-inventory/emission-inventory.component';
import { FacilityInformationComponent } from 'src/app/modules/emissions-reporting/pages/facility-information/facility-information.component';
import { ReleasePointDetailsComponent } from 'src/app/modules/emissions-reporting/pages/release-point-details/release-point-details.component';
import { EmissionsProcessDetailsComponent } from 'src/app/modules/emissions-reporting/pages/emissions-process-details/emissions-process-details.component';
import { ControlDeviceDetailsComponent } from 'src/app/modules/emissions-reporting/pages/control-device-details/control-device-details.component';
import { EmissionsUnitsSummaryComponent } from 'src/app/modules/emissions-reporting/pages/emissions-units-summary/emissions-units-summary.component';
import { ReleasePointsSummaryComponent } from 'src/app/modules/emissions-reporting/pages/release-points-summary/release-points-summary.component';
import { ControlDevicesSummaryComponent } from 'src/app/modules/emissions-reporting/pages/control-devices-summary/control-devices-summary.component';
import { ReportSummaryComponent } from 'src/app/modules/emissions-reporting/pages/report-summary/report-summary.component';
import { CreateEmissionsProcessComponent } from 'src/app/modules/emissions-reporting/pages/create-emissions-process/create-emissions-process.component';

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FacilitySiteResolverService } from 'src/app/core/services/facility-site-resolver.service';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { EmissionDetailsComponent } from 'src/app/modules/emissions-reporting/pages/emission-details/emission-details.component';

const reportRoutes: Routes = [
  {
    path: '',
    component: EmissionsReportingComponent,
    data: {breadcrumb: 'Emissions Reports'},
    children: [
      {
        path: ':reportId',
        resolve: {
          facilitySite: FacilitySiteResolverService
        },
        children: [
          {
            path: '',
            component: EmissionInventoryComponent,
            data: { title: 'Emission Inventory', breadcrumb: '&year Emissions Report'}
          }, {
            path: `${BaseReportUrl.FACILITY_INFO}`,
            component: FacilityInformationComponent,
            data: { title: 'Facility Information', breadcrumb: '&year Emissions Report'}
          }, {
            path: `${BaseReportUrl.CONTROL_DEVICE}/:controlId`,
            component: ControlDeviceDetailsComponent,
            data: { title: 'Control Device Details', breadcrumb: '&year Emissions Report'}
          }, {
            path: `${BaseReportUrl.CONTROL_DEVICE}`,
            component: ControlDevicesSummaryComponent,
            data: { title: 'Control Devices Summary', breadcrumb: '&year Emissions Report'}
          }, {
            path: `${BaseReportUrl.EMISSIONS_UNIT}/:unitId/process/create`,
            component: CreateEmissionsProcessComponent,
            data: { title: 'Emissions Unit', breadcrumb: '&year Emissions Report'}
          }, {
            path: `${BaseReportUrl.EMISSIONS_UNIT}/:unitId`,
            component: EmissionUnitDashboardComponent,
            data: { title: 'Emissions Unit', breadcrumb: '&year Emissions Report'}
          }, {
            path: `${BaseReportUrl.EMISSIONS_UNIT}`,
            component: EmissionsUnitsSummaryComponent,
            data: { title: 'Emissions Units Summary', breadcrumb: '&year Emissions Report'}
          }, {
            path: `${BaseReportUrl.EMISSIONS_PROCESS}/:processId`,
            component: EmissionsProcessDetailsComponent,
            data: { title: 'Emissions Process Details', breadcrumb: '&year Emissions Report'}
          }, {
            path: `${BaseReportUrl.REPORTING_PERIOD}/:periodId/${BaseReportUrl.EMISSION}`,
            component: EmissionDetailsComponent,
            data: { title: 'Emission Details', breadcrumb: '&year Emissions Report', create: 'true'}
          }, {
            path: `${BaseReportUrl.REPORTING_PERIOD}/:periodId/${BaseReportUrl.EMISSION}/:emissionId`,
            component: EmissionDetailsComponent,
            data: { title: 'Emission Details', breadcrumb: '&year Emissions Report', create: 'false'}
          }, {
            path: `${BaseReportUrl.RELEASE_POINT}/:releasePointId`,
            component: ReleasePointDetailsComponent,
            data: { title: 'Release Point', breadcrumb: '&year Emissions Report'}
          }, {
            path: `${BaseReportUrl.RELEASE_POINT}`,
            component: ReleasePointsSummaryComponent,
            data: { title: 'Release Points Summary', breadcrumb: '&year Emissions Report'}
          }, {
            path: `${BaseReportUrl.REPORT_SUMMARY}`,
            component: ReportSummaryComponent,
            data: { title: 'Report Summary', breadcrumb: '&year Emissions Report'}
          }, {
            path: '**',
            component: FacilityInformationComponent,
            data: { title: 'PLACEHOLDER', breadcrumb: '&year Emissions Report'}
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
export class ReportsRoutingModule {}
