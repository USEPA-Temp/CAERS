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

import { SidebarInnerNavItemComponent } from './sidebar-inner-nav-item.component';
import { SidebarInnerNavComponent } from 'src/app/modules/shared/components/sidebar-inner-nav/sidebar-inner-nav.component';

import { SharedModule } from 'src/app/modules/shared/shared.module';

import { RouterTestingModule } from '@angular/router/testing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';

describe('SidebarInnerNavItemComponent', () => {
  let component: SidebarInnerNavItemComponent;
  let fixture: ComponentFixture<SidebarInnerNavItemComponent>;
  const navItemJson = {
      id: 1,
      description: 'Boiler 001',
      baseUrl: 'emissionUnit',
      children: [
          {
              id: 1,
              description: 'Process 007',
              baseUrl: 'process',
              children: [
                  {
                      id: 1,
                      description: 'Release Point 002',
                      baseUrl: 'release',
                      children: null
                  }
              ]
          }
      ]
  };
  const navItem: SideNavItem = SideNavItem.fromJson(navItemJson);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SidebarInnerNavItemComponent,
                      SidebarInnerNavComponent ],
      imports: [NgbModule,
                SharedModule,
                RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarInnerNavItemComponent);
    component = fixture.componentInstance;
    component.navItem = navItem;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
