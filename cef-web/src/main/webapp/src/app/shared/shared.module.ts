import { FacilityInfoComponent } from './facility-info/facility-info.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faCaretRight } from '@fortawesome/free-solid-svg-icons';
import { faCaretDown } from '@fortawesome/free-solid-svg-icons';
import { faAngleLeft } from '@fortawesome/free-solid-svg-icons';
import { CollapseIconComponent } from './collapse-icon/collapse-icon.component';
import { FacilityWidgetComponent } from './facility-widget/facility-widget.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { CollapseNavComponent } from './sidebar/collapse-nav/collapse-nav.component';
import { InnerNavComponent } from './sidebar/collapse-nav/inner-nav/inner-nav.component';
import { InnerNavItemComponent } from './sidebar/collapse-nav/inner-nav/inner-nav-item/inner-nav-item.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedRoutingModule } from "src/app/shared/shared-routing";

@NgModule({
  declarations: [
    FacilityInfoComponent,
    CollapseIconComponent,
    FacilityWidgetComponent,
    SidebarComponent,
    CollapseNavComponent,
    InnerNavComponent,
    InnerNavItemComponent
  ],
  exports: [
    FacilityInfoComponent,
    CollapseIconComponent,
    FontAwesomeModule,
    SidebarComponent,
    CollapseNavComponent,
    InnerNavComponent,
    InnerNavItemComponent,
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
