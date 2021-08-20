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

import { CollapseNavComponent } from './collapse-nav.component';
import { SidebarInnerNavComponent } from 'src/app/modules/shared/components/sidebar-inner-nav/sidebar-inner-nav.component';
import { SidebarInnerNavItemComponent } from 'src/app/modules/shared/components/sidebar-inner-nav-item/sidebar-inner-nav-item.component';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';
import { SharedModule } from 'src/app/modules/shared/shared.module';

import { RouterTestingModule } from '@angular/router/testing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

describe('CollapseNavComponent', () => {
  let component: CollapseNavComponent;
  let fixture: ComponentFixture<CollapseNavComponent>;
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
  const navItems: SideNavItem[] = [SideNavItem.fromJson(navItemJson)];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollapseNavComponent,
                      SidebarInnerNavComponent,
                      SidebarInnerNavItemComponent ],
      imports: [NgbModule,
                SharedModule,
                RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollapseNavComponent);
    component = fixture.componentInstance;
    component.collapsed = false;
    component.navItems = navItems;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
