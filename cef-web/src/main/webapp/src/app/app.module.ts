import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { AppComponent } from 'src/app/app.component';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { FacilityDashboardComponent } from 'src/app/modules/facility-dashboard/pages/facility-dashboard/facility-dashboard.component';
import { HeaderComponent } from 'src/app/core/header/header.component';
import { FooterComponent } from 'src/app/core/footer/footer.component';
import { FacilityListComponent } from 'src/app/modules/facility-dashboard/components/facility-list/facility-list.component';
import { NotificationListComponent } from 'src/app/shared/components/notification-list/notification-list.component';
import { FacilityListItemComponent } from 'src/app/modules/facility-dashboard/components/facility-list-item/facility-list-item.component';
import { SharedModule } from './shared/shared.module';
import { BreadcrumbNavComponent } from 'src/app/shared/components/breadcrumb-nav/breadcrumb-nav.component';
import { SubmissionReviewDashboardComponent } from 'src/app/modules/submission-review-dashboard/pages/submission-review-dashboard/submission-review-dashboard.component';
import { SubmissionReviewListComponent } from 'src/app/modules/submission-review-dashboard/components/submission-review-list/submission-review-list.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RedirectComponent } from 'src/app/modules/redirect/redirect.component';
import { FacilityDataReviewComponent } from 'src/app/modules/facility-dashboard/components/facility-data-review/facility-data-review.component';


@NgModule({
  declarations: [
    AppComponent,
    FacilityDashboardComponent,
    SubmissionReviewDashboardComponent,
    RedirectComponent,
    HeaderComponent,
    FooterComponent,
    FacilityListComponent,
    NotificationListComponent,
    FacilityListItemComponent,
    BreadcrumbNavComponent,
    SubmissionReviewListComponent,
    FacilityDataReviewComponent
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
