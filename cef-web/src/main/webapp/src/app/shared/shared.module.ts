import { FacilityInfoComponent } from './facility-info/facility-info.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faCaretRight } from '@fortawesome/free-solid-svg-icons';
import { faCaretDown } from '@fortawesome/free-solid-svg-icons';
import { faAngleLeft } from '@fortawesome/free-solid-svg-icons';
import { CollapseIconComponent } from './collapse-icon/collapse-icon.component';

@NgModule({
  declarations: [
    FacilityInfoComponent,
    CollapseIconComponent
  ],
  exports: [
    FacilityInfoComponent,
    CollapseIconComponent
  ],
  imports: [
    CommonModule,
    FontAwesomeModule
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
