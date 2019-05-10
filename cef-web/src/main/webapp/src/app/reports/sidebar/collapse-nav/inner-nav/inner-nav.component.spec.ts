import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InnerNavComponent } from './inner-nav.component';
import { InnerNavItemComponent } from './inner-nav-item/inner-nav-item.component';

import { SideNavItem } from "src/app/reports/model/side-nav-item";

import { SharedModule } from "src/app/shared/shared.module";

import { RouterTestingModule } from '@angular/router/testing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

describe('InnerNavComponent', () => {
  let component: InnerNavComponent;
  let fixture: ComponentFixture<InnerNavComponent>;
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
      declarations: [ InnerNavComponent,
                      InnerNavItemComponent ],
      imports: [NgbModule,
                SharedModule,
                RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InnerNavComponent);
    component = fixture.componentInstance;
    component.navItems = navItems;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
