import { SharedModule } from './modules/shared/shared.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule, HTTP_INTERCEPTORS, HttpClientJsonpModule, HttpClientXsrfModule} from '@angular/common/http';
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
import { FontAwesomeModule, FaConfig, FaIconLibrary  } from '@fortawesome/angular-fontawesome';
import {faUserCircle, faQuestionCircle, faBan, fas, faPlus} from '@fortawesome/free-solid-svg-icons';
import { RedirectComponent } from 'src/app/modules/dashboards/pages/redirect/redirect.component';
import { FacilityDataReviewComponent } from 'src/app/modules/dashboards/components/facility-data-review/facility-data-review.component';
import { GlobalErrorHandlerService } from 'src/app/core/services/global-error-handler.service';
import { HttpErrorInterceptor } from 'src/app/core/interceptors/http-error.interceptor';
import { ReportSummaryModalComponent } from 'src/app/modules/dashboards/components/report-summary-modal/report-summary-modal.component';
import { BusyModalComponent } from './shared/components/busy-modal/busy-modal.component';
import { RejectSubmissionModalComponent } from './modules/dashboards/components/reject-submission-modal/reject-submission-modal.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { TimeoutModalComponent } from './shared/components/timeout-modal/timeout-modal.component';
import {XhrInterceptor} from "./core/interceptors/http-xhr.interceptor";
import { ConfirmationDialogComponent } from './shared/components/confirmation-dialog/confirmation-dialog.component';
import { SubmissionReviewModalComponent } from './modules/dashboards/components/submission-review-modal/submission-review-modal.component';
import { HelpPageComponent } from './modules/dashboards/pages/help-page/help-page.component';
import { AdminPropertiesComponent } from './modules/dashboards/pages/admin-properties/admin-properties.component';
import { AdminAnnouncementPropertiesComponent } from './modules/dashboards/pages/admin-announcement-properties/admin-announcement-properties.component';
import { ReviewerNavComponent } from './modules/dashboards/components/reviewer-nav/reviewer-nav.component';
import {AdminNavComponent} from "./modules/shared/components/admin-nav/admin-nav.component";
import { EisSubmissionComponent } from './modules/dashboards/pages/eis-submission/eis-submission.component';
import { EisTransactionsComponent } from './modules/dashboards/pages/eis-transactions/eis-transactions.component';
import { SortByPipe } from './shared/pipes/sort-by.pipe';
import { DataFilterPipe } from './shared/pipes/data-filter.pipe';
import { RecalculateEmissionTonsModalComponent } from './modules/dashboards/components/recalculate-emission-tons-modal/recalculate-emission-tons-modal.component';
import { SltPropertiesComponent } from 'src/app/modules/dashboards/pages/slt-properties/slt-properties.component';
import { AdminUserFeedbackComponent } from './modules/dashboards/pages/admin-user-feedback/admin-user-feedback.component';
import { UserFeedbackReportModalComponent } from './modules/dashboards/components/user-feedback-report-modal/user-feedback-report-modal.component';


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
    RejectSubmissionModalComponent,
    TimeoutModalComponent,
    ConfirmationDialogComponent,
    SubmissionReviewModalComponent,
    HelpPageComponent,
    AdminPropertiesComponent,
    AdminAnnouncementPropertiesComponent,
    AdminUserFeedbackComponent,
    ReviewerNavComponent,
    EisSubmissionComponent,
    EisTransactionsComponent,
    SortByPipe,
    DataFilterPipe,
    RecalculateEmissionTonsModalComponent,
    UserFeedbackReportModalComponent,
    SltPropertiesComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    HttpClientModule,
    HttpClientXsrfModule,
    HttpClientJsonpModule,
    AppRoutingModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  entryComponents: [
    ReportSummaryModalComponent,
    BusyModalComponent,
    RejectSubmissionModalComponent,
    TimeoutModalComponent,
    ConfirmationDialogComponent,
    SubmissionReviewModalComponent,
    RecalculateEmissionTonsModalComponent,
    UserFeedbackReportModalComponent
  ],
  providers: [
      {provide: ErrorHandler, useClass: GlobalErrorHandlerService},
      {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true},
      {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
    constructor(config: FaConfig, library: FaIconLibrary) {
        config.fallbackIcon = faBan;
        library.addIconPacks(fas);
        library.addIcons(faUserCircle, faQuestionCircle, faPlus)
      }
}
