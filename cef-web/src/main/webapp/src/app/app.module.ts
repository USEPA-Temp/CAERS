import { SharedModule } from './modules/shared/shared.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClientJsonpModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { AppComponent } from 'src/app/app.component';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { FacilityDashboardComponent } from 'src/app/modules/dashboards/pages/facility-dashboard/facility-dashboard.component';
import { HeaderComponent } from 'src/app/core/header/header.component';
import { FooterComponent } from 'src/app/core/footer/footer.component';
import { FacilityListComponent } from 'src/app/modules/dashboards/components/facility-list/facility-list.component';
import { NotificationListComponent } from 'src/app/shared/components/notification-list/notification-list.component';
import { FacilityListItemComponent } from 'src/app/modules/dashboards/components/facility-list-item/facility-list-item.component';
import { BreadcrumbNavComponent } from 'src/app/shared/components/breadcrumb-nav/breadcrumb-nav.component';
import { SubmissionReviewDashboardComponent } from 'src/app/modules/dashboards/pages/submission-review-dashboard/submission-review-dashboard.component';
import { SubmissionReviewListComponent } from 'src/app/modules/dashboards/components/submission-review-list/submission-review-list.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faUserCircle } from '@fortawesome/free-solid-svg-icons';
import { RedirectComponent } from 'src/app/modules/dashboards/pages/redirect/redirect.component';
import { FacilityDataReviewComponent } from 'src/app/modules/dashboards/components/facility-data-review/facility-data-review.component';
import { GlobalErrorHandlerService } from 'src/app/core/services/global-error-handler.service';
import { HttpErrorInterceptor } from 'src/app/core/interceptors/http-error.interceptor';
import { ReportSummaryModalComponent } from 'src/app/modules/dashboards/components/report-summary-modal/report-summary-modal.component';
import { BusyModalComponent } from './shared/components/busy-modal/busy-modal.component';
import { DeleteDialogComponent } from './shared/components/delete-dialog/delete-dialog.component';
import { RejectSubmissionModalComponent } from './modules/dashboards/components/reject-submission-modal/reject-submission-modal.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { TimeoutModalComponent } from './shared/components/timeout-modal/timeout-modal.component';
import {XhrInterceptor} from "./core/interceptors/http-xhr.interceptor";


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
    FacilityDataReviewComponent,
    ReportSummaryModalComponent,
    BusyModalComponent,
    DeleteDialogComponent,
    RejectSubmissionModalComponent,
    TimeoutModalComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    HttpClientModule,
    HttpClientJsonpModule,
    AppRoutingModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  entryComponents: [
    ReportSummaryModalComponent,
    BusyModalComponent,
    DeleteDialogComponent,
    RejectSubmissionModalComponent,
    TimeoutModalComponent
  ],
  providers: [
      {provide: ErrorHandler, useClass: GlobalErrorHandlerService},
      {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true},
      {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}
    ],
  bootstrap: [AppComponent]
})
export class AppModule {
    constructor() {
        library.add(faUserCircle);
      }
}
