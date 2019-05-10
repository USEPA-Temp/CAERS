import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { FacilityDashboardComponent } from './facility-dashboard/facility-dashboard.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { FacilityListComponent } from './facility-dashboard/facility-list/facility-list.component';
import { NotificationListComponent } from './components/notification-list/notification-list.component';
import { FacilityListItemComponent } from './facility-dashboard/facility-list/facility-list-item/facility-list-item.component';
import { SharedModule } from './shared/shared.module';
import { BreadcrumbNavComponent } from './components/breadcrumb-nav/breadcrumb-nav.component';
import { SubmissionReviewDashboardComponent } from './submission-review-dashboard/submission-review-dashboard.component';
import { SubmissionReviewListComponent } from './submission-review-dashboard/submission-review-list/submission-review-list.component';
import { SortableHeader } from './sortable.directive';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [
    AppComponent,
    FacilityDashboardComponent,
    SubmissionReviewDashboardComponent,
    HeaderComponent,
    FooterComponent,
    FacilityListComponent,
    NotificationListComponent,
    FacilityListItemComponent,
    BreadcrumbNavComponent,
    SubmissionReviewListComponent,
    SortableHeader
  ],
  imports: [
    BrowserModule,
    SharedModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    HttpClientModule,
    AppRoutingModule,
    FontAwesomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
