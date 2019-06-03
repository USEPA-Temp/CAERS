import { FacilityInfoComponent } from 'src/app/shared/components/facility-info/facility-info.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faCaretRight } from '@fortawesome/free-solid-svg-icons';
import { faCaretDown } from '@fortawesome/free-solid-svg-icons';
import { faAngleLeft } from '@fortawesome/free-solid-svg-icons';
import { CollapseIconComponent } from 'src/app/shared/components/collapse-icon/collapse-icon.component';
import { FacilityWidgetComponent } from 'src/app/shared/components/facility-widget/facility-widget.component';
import { SidebarComponent } from 'src/app/shared/components/sidebar/sidebar.component';
import { CollapseNavComponent } from 'src/app/shared/components/collapse-nav/collapse-nav.component';
import { SidebarInnerNavComponent } from 'src/app/shared/components/sidebar-inner-nav/sidebar-inner-nav.component';
import { SidebarInnerNavItemComponent } from 'src/app/shared/components/sidebar-inner-nav-item/sidebar-inner-nav-item.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedRoutingModule } from 'src/app/shared/shared-routing';

@NgModule({
  declarations: [
    FacilityInfoComponent,
    CollapseIconComponent,
    FacilityWidgetComponent,
    SidebarComponent,
    CollapseNavComponent,
    SidebarInnerNavComponent,
    SidebarInnerNavItemComponent
  ],
  exports: [
    FacilityInfoComponent,
    CollapseIconComponent,
    FontAwesomeModule,
    SidebarComponent,
    CollapseNavComponent,
    SidebarInnerNavComponent,
    SidebarInnerNavItemComponent,
    FacilityWidgetComponent
  ],
  imports: [
    CommonModule,
    FontAwesomeModule,
    NgbModule,
    SharedRoutingModule
  ]
})
export class SharedModule {
  constructor() {
    // Add an icon to the library for convenient access in other components
    library.add(faCaretRight);
    library.add(faCaretDown);
    library.add(faAngleLeft);
  }
}
