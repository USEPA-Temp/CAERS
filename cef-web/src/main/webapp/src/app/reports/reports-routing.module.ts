import { ReportsDashboardComponent } from './reports-dashboard/reports-dashboard.component';
import { ReportsComponent } from './reports/reports.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const reportRoutes: Routes = [
  {
    path: '',
    component: ReportsComponent,
    children: [
      {
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
