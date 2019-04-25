import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule }    from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { FacilityDashboardComponent } from './facility-dashboard/facility-dashboard.component';
import { FacilityInfoComponent } from './components/facility-info/facility-info.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { FacilityListComponent } from './facility-dashboard/facility-list/facility-list.component';
import { NotificationListComponent } from './facility-dashboard/notification-list/notification-list.component';

@NgModule({
  declarations: [
    AppComponent,
    FacilityDashboardComponent,
    FacilityInfoComponent,
    HeaderComponent,
    FooterComponent,
    FacilityListComponent,
    NotificationListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
