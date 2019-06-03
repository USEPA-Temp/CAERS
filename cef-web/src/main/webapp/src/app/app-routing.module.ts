import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { FacilityDashboardComponent } from 'src/app/modules/facility-dashboard/pages/facility-dashboard/facility-dashboard.component';
import { FacilityDataReviewComponent } from 'src/app/modules/facility-dashboard/components/facility-data-review/facility-data-review.component';
import { SubmissionReviewDashboardComponent } from 'src/app/modules/submission-review-dashboard/pages/submission-review-dashboard/submission-review-dashboard.component';
import { FacilityResolverService } from 'src/app/core/services/facility-resolver.service';
import { RedirectComponent } from 'src/app/modules/redirect/redirect.component';

const routes: Routes = [
  { path: '', component: RedirectComponent, data: { title: 'Redirect Page' } },
  {
    path: 'facility',
    children: [
      {
        path: ':facilityId',
        resolve: {
          facility: FacilityResolverService
        },
        children: [
          {
            path: 'reports',
            loadChildren: 'src/app/modules/reports/reports.module#ReportsModule'
          },
          {
              path: 'review',
              component: FacilityDataReviewComponent
            }
        ]
      }, {
        path: '',
        component: FacilityDashboardComponent,
        data: { title: 'Facility Dashboard' },
      }
    ]
  },
  { path: 'submissionReviewDashboard', component: SubmissionReviewDashboardComponent, data: { title: 'Submission Review Dashboard' } },
  { path: '*', component: RedirectComponent, data: { title: 'Redirect Page' } }
];

@NgModule({
  // useHash is required for spring and inheritanceStrategy allows children to reference data from parents
  imports: [ RouterModule.forRoot(routes, {useHash: true, paramsInheritanceStrategy: 'always'}) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}
