import { FacilityInfoComponent } from './facility-info/facility-info.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    FacilityInfoComponent
  ],
  exports: [
    FacilityInfoComponent
  ],
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
