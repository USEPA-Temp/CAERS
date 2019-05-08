import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { FacilityDashboardComponent } from './facility-dashboard/facility-dashboard.component';
import { SubmissionReviewDashboardComponent } from './submission-review-dashboard/submission-review-dashboard.component';

const routes: Routes = [
  { path: '', redirectTo: '/facilityDashboard', pathMatch: 'full' },
  { path: 'facilityDashboard', component: FacilityDashboardComponent, data: { title: 'Facility Dashboard' } },
  { path: 'submissionReviewDashboard', component: SubmissionReviewDashboardComponent, data: { title: 'Submission Review Dashboard' } }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes, {useHash: true, enableTracing: true}) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
