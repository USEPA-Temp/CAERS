import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { FacilityDashboardComponent } from './facility-dashboard/facility-dashboard.component';

const routes: Routes = [
  { path: '', redirectTo: '/facilityDashboard', pathMatch: 'full' },
  { path: 'facilityDashboard', component: FacilityDashboardComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes, {useHash: true}) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
