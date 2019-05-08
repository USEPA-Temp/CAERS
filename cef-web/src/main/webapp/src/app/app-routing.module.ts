import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { FacilityDashboardComponent } from './facility-dashboard/facility-dashboard.component';
import { SubmissionReviewDashboardComponent } from './submission-review-dashboard/submission-review-dashboard.component';
import { FacilityResolverService } from './services/facility-resolver.service';

const routes: Routes = [
  { path: '', redirectTo: '/facility', pathMatch: 'full' },
  {
    path: 'facility',
    children: [
      {
        path: ':id',
        resolve: {
          facility: FacilityResolverService
        },
        children: [
          {
            path: 'reports',
            loadChildren: './reports/reports.module#ReportsModule'
          }
        ]
      }, {
        path: '',
        component: FacilityDashboardComponent,
        data: { title: 'Facility Dashboard' },
      }
    ]
  },
  { path: 'submissionReviewDashboard', component: SubmissionReviewDashboardComponent, data: { title: 'Submission Review Dashboard' } }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes, {useHash: true, enableTracing: true}) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
