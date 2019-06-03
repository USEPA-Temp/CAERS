import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarInnerNavItemComponent } from './sidebar-inner-nav-item.component';
import { SidebarInnerNavComponent } from 'src/app/shared/components/sidebar-inner-nav/sidebar-inner-nav.component';

import { SharedModule } from 'src/app/shared/shared.module';

import { RouterTestingModule } from '@angular/router/testing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';

describe('SidebarInnerNavItemComponent', () => {
  let component: SidebarInnerNavItemComponent;
  let fixture: ComponentFixture<SidebarInnerNavItemComponent>;
  let navItemJson = {
      "id": 1,
      "description": "Boiler 001",
      "baseUrl": "emissionUnit",
      "children": [
          {
              "id": 1,
              "description": "Process 007",
              "baseUrl": "process",
              "children": [
                  {
                      "id": 1,
                      "description": "Release Point 002",
                      "baseUrl": "release",
                      "children": null
                  }
              ]
          }
      ]
  };
  let navItem: SideNavItem = SideNavItem.fromJson(navItemJson);

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
