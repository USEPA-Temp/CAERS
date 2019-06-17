import { EmissionUnitDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emission-unit-dashboard/emission-unit-dashboard.component';
import { EmissionsReportingDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting-dashboard/emissions-reporting-dashboard.component';
import { EmissionsReportingComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting/emissions-reporting.component';
import { EmissionInventoryComponent } from 'src/app/modules/emissions-reporting/pages/emission-inventory/emission-inventory.component';
import { FacilityInformationComponent } from 'src/app/modules/emissions-reporting/pages/facility-information/facility-information.component';
import { ReleasePointDetailsComponent } from 'src/app/modules/emissions-reporting/pages/release-point-details/release-point-details.component';
import { EmissionsProcessDetailsComponent } from 'src/app/modules/emissions-reporting/pages/emissions-process-details/emissions-process-details.component';

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FacilitySiteResolverService } from 'src/app/core/services/facility-site-resolver.service';

const reportRoutes: Routes = [
  {
    path: '',
    component: EmissionsReportingComponent,
    children: [
      {
        path: ':reportId',
        resolve:{
          facilitySite: FacilitySiteResolverService
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
          }, {
            path: 'emissionUnit/:unitId',
            component: EmissionUnitDashboardComponent,
            data: { title: 'Emission Unit' }
          }, {
            path: 'process/:processId',
            component: EmissionsProcessDetailsComponent,
            data: { title: 'Emissions Process Details' }
          }, {
            path: 'release/:releasePointId',
            component: ReleasePointDetailsComponent,
            data: { title: 'Release Point' }
          }, {
            path: '**',
            component: FacilityInformationComponent,
            data: { title: 'PLACEHOLDER' }
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
