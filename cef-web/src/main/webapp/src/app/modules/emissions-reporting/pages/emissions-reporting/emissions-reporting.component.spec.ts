/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsReportingComponent } from './emissions-reporting.component';
import { EmissionsReportingDashboardComponent } from 'src/app/modules/emissions-reporting/pages/emissions-reporting-dashboard/emissions-reporting-dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { SidebarComponent } from 'src/app/modules/shared/components/sidebar/sidebar.component';
import { CollapseNavComponent } from 'src/app/modules/shared/components/collapse-nav/collapse-nav.component';
import { SidebarInnerNavComponent } from 'src/app/modules/shared/components/sidebar-inner-nav/sidebar-inner-nav.component';
import { SidebarInnerNavItemComponent } from 'src/app/modules/shared/components/sidebar-inner-nav-item/sidebar-inner-nav-item.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/modules/shared/shared.module';


describe('EmissionsReportingComponent', () => {
  let component: EmissionsReportingComponent;
  let fixture: ComponentFixture<EmissionsReportingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionsReportingComponent,
                      EmissionsReportingDashboardComponent,
                      SidebarComponent,
                      CollapseNavComponent,
                      SidebarInnerNavComponent,
                      SidebarInnerNavItemComponent],
      imports: [HttpClientModule,
                RouterTestingModule,
                NgbModule,
                SharedModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsReportingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
