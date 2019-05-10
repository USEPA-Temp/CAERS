import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CollapseNavComponent } from './collapse-nav.component';
import { InnerNavComponent } from './inner-nav/inner-nav.component';
import { InnerNavItemComponent } from './inner-nav/inner-nav-item/inner-nav-item.component';

import { SideNavItem } from "src/app/reports/model/side-nav-item";

import { SharedModule } from "src/app/shared/shared.module";

import { RouterTestingModule } from '@angular/router/testing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

describe('CollapseNavComponent', () => {
  let component: CollapseNavComponent;
  let fixture: ComponentFixture<CollapseNavComponent>;
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
  let navItems: SideNavItem[] = [SideNavItem.fromJson(navItemJson)];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollapseNavComponent,
                      InnerNavComponent,
                      InnerNavItemComponent ],
      imports: [NgbModule,
                SharedModule,
                RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollapseNavComponent);
    component = fixture.componentInstance;
    component.collapsed=false;
    component.navItems = navItems;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
