import { EmissionInventoryComponent } from './emission-inventory/emission-inventory.component';
import { EmissionUnitDashboardComponent } from './emission-unit-dashboard/emission-unit-dashboard.component';
import { FacilityInformationComponent } from './facility-information/facility-information.component';
import { ReportsDashboardComponent } from './reports-dashboard/reports-dashboard.component';
import { ReportsComponent } from './reports.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const reportRoutes: Routes = [
  {
    path: '',
    component: ReportsComponent,
    children: [
      {
        path: 'emissionUnit/:unitId',
        children: [
          {
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
        component: ReportsDashboardComponent,
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
