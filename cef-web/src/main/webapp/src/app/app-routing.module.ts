import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FacilityDashboardComponent} from 'src/app/modules/dashboards/pages/facility-dashboard/facility-dashboard.component';
import {FacilityDataReviewComponent} from 'src/app/modules/dashboards/components/facility-data-review/facility-data-review.component';
import {SubmissionReviewDashboardComponent} from 'src/app/modules/dashboards/pages/submission-review-dashboard/submission-review-dashboard.component';
import {EisSubmissionComponent} from "src/app/modules/dashboards/pages/eis-submission/eis-submission.component";
import {FacilityResolverService} from 'src/app/core/services/facility-resolver.service';
import {RedirectComponent} from 'src/app/modules/dashboards/pages/redirect/redirect.component';
import {ErrorComponent} from 'src/app/modules/shared/pages/error/error.component';
import {BulkUploadComponent} from 'src/app/modules/shared/pages/bulk-upload/bulk-upload.component';
import {HelpPageComponent} from 'src/app/modules/dashboards/pages/help-page/help-page.component';
import {AdminPropertiesComponent} from 'src/app/modules/dashboards/pages/admin-properties/admin-properties.component';
import {AdminAnnouncementPropertiesComponent} from 'src/app/modules/dashboards/pages/admin-announcement-properties/admin-announcement-properties.component';
import { AdminAuthGuard } from 'src/app/core/guards/admin-auth.guard';
import { ReviewerAuthGuard } from 'src/app/core/guards/reviewer-auth.guard';
import {EisTransactionsComponent} from "src/app/modules/dashboards/pages/eis-transactions/eis-transactions.component";
import { SltPropertiesComponent } from 'src/app/modules/dashboards/pages/slt-properties/slt-properties.component';

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
            loadChildren: () => import('src/app/modules/emissions-reporting/emissions-reporting.module').then(m => m.EmissionsReportingModule)
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
  {
    path: 'admin',
    canActivate: [AdminAuthGuard],
    children: [
      {
        path: 'properties',
        component: AdminPropertiesComponent,
        data: { title: 'Admin Properties' },
      }, {
        path: 'announcement',
        component: AdminAnnouncementPropertiesComponent,
        data: { title: 'Announcement Banner' },
      }, {
        path: 'upload',
        component: BulkUploadComponent,
        data: { title: 'Bulk Upload' },
      }, {
        path: '',
        redirectTo: 'properties',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: 'reviewer',
    canActivateChild: [ReviewerAuthGuard],
    children: [
      {
        path: 'dashboard',
        component: SubmissionReviewDashboardComponent,
        data: { title: 'Submission Review Dashboard' },
      }, {
        path: 'eisSubmitData',
        component: EisSubmissionComponent,
        data: { title: 'Submit Data' },
      }, {
        path: 'eisTransactions',
        component: EisTransactionsComponent,
        data: { title: 'Review Transactions' },
      }, {
        path: 'properties',
        component: SltPropertiesComponent,
        data: { title: 'Agency Administration' },
      }, {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }
    ]
  },
  { path: 'helpPage', component: HelpPageComponent, data: { title: 'Help Page' } },
  { path: 'upload', redirectTo: 'admin/upload', pathMatch: 'full' },
  { path: 'error', component: ErrorComponent, data: { title: 'Error Page' } },
  { path: '*', component: RedirectComponent, data: { title: 'Redirect Page' } }
];

@NgModule({
  // useHash is required for spring and inheritanceStrategy allows children to reference data from parents
  imports: [ RouterModule.forRoot(routes, {useHash: true, paramsInheritanceStrategy: 'always'}) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}
