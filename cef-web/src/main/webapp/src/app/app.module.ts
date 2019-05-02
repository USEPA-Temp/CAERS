import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule }    from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { FacilityDashboardComponent } from './facility-dashboard/facility-dashboard.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { FacilityListComponent } from './facility-dashboard/facility-list/facility-list.component';
import { NotificationListComponent } from './facility-dashboard/notification-list/notification-list.component';
import { FacilityListItemComponent } from './facility-dashboard/facility-list/facility-list-item/facility-list-item.component';
import { SharedModule } from './shared/shared.module';
import { BreadcrumbNavComponent } from './components/breadcrumb-nav/breadcrumb-nav.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { CollapseNavComponent } from './components/sidebar/collapse-nav/collapse-nav.component';

@NgModule({
  declarations: [
    AppComponent,
    FacilityDashboardComponent,
    HeaderComponent,
    FooterComponent,
    FacilityListComponent,
    NotificationListComponent,
    FacilityListItemComponent,
    BreadcrumbNavComponent,
    SidebarComponent,
    CollapseNavComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
