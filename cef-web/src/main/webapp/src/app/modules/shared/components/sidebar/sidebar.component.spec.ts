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

import { SidebarComponent } from './sidebar.component';
import { CollapseNavComponent } from 'src/app/modules/shared/components/collapse-nav/collapse-nav.component';
import { SidebarInnerNavComponent } from 'src/app/modules/shared/components/sidebar-inner-nav/sidebar-inner-nav.component';
import { SidebarInnerNavItemComponent } from 'src/app/modules/shared/components/sidebar-inner-nav-item/sidebar-inner-nav-item.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';


describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;
  const facility = {
      cdxFacilityId: 123,
      epaRegistryId: '123',
      programId: '123',
      facilityName: 'test-facility',
      address: '123 elm st',
      address2: '',
      city: 'Fairfax',
      state: 'Virginia',
      county: 'US',
      zipCode: '22033'
  };
  const report = {
      id: 1,
      facilityId: '123',
      status: 'InProgress',
      year: 2019
  };


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SidebarComponent,
                      CollapseNavComponent,
                      SidebarInnerNavComponent,
                      SidebarInnerNavItemComponent ],
      imports: [NgbModule,
                SharedModule,
                RouterTestingModule,
                HttpClientModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarComponent);
    component = fixture.componentInstance;
    component.facility = facility;
    component.report = report;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
