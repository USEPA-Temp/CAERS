import { PhoneNumberPipe } from 'src/app/modules/shared//pipes/phone-number.pipe';
import { FacilityInfoComponent } from 'src/app/shared/components/facility-info/facility-info.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import {
   faCaretRight,
   faEdit,
   faCaretDown,
   faAngleLeft,
   faTrashAlt,
   faPlus,
   faCircle,
   faInfoCircle,
   faCheck,
   faTimesCircle,
   faFilter, faTimes, faSearch
} from '@fortawesome/free-solid-svg-icons';
import { CollapseIconComponent } from 'src/app/modules/shared/components/collapse-icon/collapse-icon.component';
import { FacilityWidgetComponent } from 'src/app/modules/shared/components/facility-widget/facility-widget.component';
import { SidebarComponent } from 'src/app/modules/shared/components/sidebar/sidebar.component';
import { CollapseNavComponent } from 'src/app/modules/shared/components/collapse-nav/collapse-nav.component';
import { SidebarInnerNavComponent } from 'src/app/modules/shared/components/sidebar-inner-nav/sidebar-inner-nav.component';
import { SidebarInnerNavItemComponent } from 'src/app/modules/shared/components/sidebar-inner-nav-item/sidebar-inner-nav-item.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedRoutingModule } from 'src/app/modules/shared/shared-routing';
import { SortableHeaderDirective } from 'src/app/shared/directives/sortable.directive';
import { ErrorComponent } from './pages/error/error.component';
import { ReportSummaryTableComponent } from 'src/app/modules/shared/components/report-summary-table/report-summary-table.component';
import { ValidationMessageComponent } from './components/validation-message/validation-message.component';
import { WholeNumberValidatorDirective } from './directives/whole-number-validator.directive';
import { NumberValidatorDirective } from './directives/number-validator.directive';
import { BaseConfirmationModalComponent } from './components/base-confirmation-modal/base-confirmation-modal.component';
import { BulkUploadComponent } from 'src/app/modules/shared/pages/bulk-upload/bulk-upload.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StripPeriodEndingPipe } from './pipes/strip-period-ending.pipe';
import { LegacyUomValidatorDirective } from './directives/legacy-uom-validator.directive';
import { FileAttachmentModalComponent } from './components/file-attachment-modal/file-attachment-modal.component';
import { ReportAttachmentTableComponent } from './components/report-attachment-table/report-attachment-table.component';
import { AdminNavComponent } from './components/admin-nav/admin-nav.component';

@NgModule({
  declarations: [
    FacilityInfoComponent,
    CollapseIconComponent,
    FacilityWidgetComponent,
    SidebarComponent,
    CollapseNavComponent,
    SidebarInnerNavComponent,
    SidebarInnerNavItemComponent,
    SortableHeaderDirective,
    PhoneNumberPipe,
    ErrorComponent,
    ReportSummaryTableComponent,
    ReportAttachmentTableComponent,
    ValidationMessageComponent,
    WholeNumberValidatorDirective,
    NumberValidatorDirective,
    BaseConfirmationModalComponent,
    FileAttachmentModalComponent,
    BulkUploadComponent,
    StripPeriodEndingPipe,
    LegacyUomValidatorDirective,
    AdminNavComponent
],
    exports: [
        FacilityInfoComponent,
        CollapseIconComponent,
        FontAwesomeModule,
        SidebarComponent,
        CollapseNavComponent,
        SidebarInnerNavComponent,
        SidebarInnerNavItemComponent,
        FacilityWidgetComponent,
        SortableHeaderDirective,
        PhoneNumberPipe,
        ReportSummaryTableComponent,
        ReportAttachmentTableComponent,
        ValidationMessageComponent,
        StripPeriodEndingPipe,
        AdminNavComponent
    ],
  imports: [
    CommonModule,
    FontAwesomeModule,
    NgbModule,
    SharedRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  entryComponents: [
    BaseConfirmationModalComponent,
    FileAttachmentModalComponent
  ]
})
export class SharedModule {
  constructor() {
    // Add an icon to the library for convenient access in other components
    library.add(faCaretRight);
    library.add(faCaretDown);
    library.add(faAngleLeft);
    library.add(faTrashAlt);
    library.add(faPlus);
    library.add(faEdit);
    library.add(faCircle);
    library.add(faInfoCircle);
    library.add(faSearch);
    library.add(faCheck);
    library.add(faTimesCircle);
    library.add(faTimes);
    library.add(faFilter);
  }
}
