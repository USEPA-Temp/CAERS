import { EmissionUnitDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emission-unit-dashboard/emission-unit-dashboard.component';
import { EmissionsReportingDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting-dashboard/emissions-reporting-dashboard.component';
import { EmissionsReportingComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting/emissions-reporting.component';
import { EmissionInventoryComponent } from 'src/app/modules/emissions-reporting/pages/emission-inventory/emission-inventory.component';
import { FacilityInformationComponent } from 'src/app/modules/emissions-reporting/pages/facility-information/facility-information.component';
import { ReleasePointDetailsComponent } from 'src/app/modules/emissions-reporting/pages/release-point-details/release-point-details.component';

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmissionsReportResolverService } from "src/app/core/services/emissions-report-resolver.service";


const reportRoutes: Routes = [
  {
    path: '',
    component: EmissionsReportingComponent,
    children: [
      {
        path: 'emissionUnit/:unitId',
        resolve:{
            emissionsReport: EmissionsReportResolverService
        },        
        children: [
          {
            path: 'process/:processId/release/:releasePointId',
            component: ReleasePointDetailsComponent,
            data: { title: 'Release Point' }
          }, {
            path: '',
            component: EmissionUnitDashboardComponent,
            data: { title: 'Emission Unit' }
          }, {
            path: '**',
            component: EmissionUnitDashboardComponent,
            data: { title: 'PLACEHOLDER' }
          }
        ]
      }, {
        path: ':reportId',
        resolve:{
            emissionsReport: EmissionsReportResolverService
        },
        children: [
          {
            path: '',
            component: EmissionInventoryComponent,
            data: { title: 'Emission Inventory' }
          }, {
            path: 'facilityInformation',
            component: FacilityInformationComponent,
            data: { title: 'Facility Information' }
          }
        ]
      }, {
        path: '',
        component: EmissionsReportingDashboardComponent,
        data: { title: 'Emission Reports Dashboard' }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(reportRoutes)],
  exports: [RouterModule]
})
export class ReportsRoutingModule { }
