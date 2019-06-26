import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FacilityDashboardComponent } from 'src/app/modules/dashboards/pages/facility-dashboard/facility-dashboard.component';
import { FacilityDataReviewComponent } from 'src/app/modules/dashboards/components/facility-data-review/facility-data-review.component';
import { SubmissionReviewDashboardComponent } from 'src/app/modules/dashboards/pages/submission-review-dashboard/submission-review-dashboard.component';
import { FacilityResolverService } from 'src/app/core/services/facility-resolver.service';
import { RedirectComponent } from 'src/app/modules/dashboards/pages/redirect/redirect.component';
import { ErrorComponent } from 'src/app/modules/shared/pages/error/error.component';

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
            path: 'report',
            loadChildren: 'src/app/modules/emissions-reporting/emissions-reporting.module#EmissionsReportingModule'
          },
          {
              path: 'review',
              component: FacilityDataReviewComponent,
              data: { breadcrumb: 'Facility Information' }
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
  { path: 'error', component: ErrorComponent, data: { title: 'Error Page' } },
  { path: '*', component: RedirectComponent, data: { title: 'Redirect Page' } }
];

@NgModule({
  // useHash is required for spring and inheritanceStrategy allows children to reference data from parents
  imports: [ RouterModule.forRoot(routes, {useHash: true, paramsInheritanceStrategy: 'always'}) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}
