import { EmissionUnitDashboardComponent } from 'src/app/modules/reports/pages/emission-unit-dashboard/emission-unit-dashboard.component';
import { ReportsDashboardComponent } from 'src/app/modules/reports/pages/reports-dashboard/reports-dashboard.component';
import { ReportsComponent } from 'src/app/modules/reports/pages/reports/reports.component';
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
