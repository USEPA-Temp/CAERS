import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InnerNavItemComponent } from './inner-nav-item.component';
import { InnerNavComponent } from '../inner-nav.component';

import { SideNavItem } from "src/app/reports/model/side-nav-item";

import { SharedModule } from "src/app/shared/shared.module";

import { RouterTestingModule } from '@angular/router/testing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

describe('InnerNavItemComponent', () => {
  let component: InnerNavItemComponent;
  let fixture: ComponentFixture<InnerNavItemComponent>;
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
      declarations: [ InnerNavItemComponent,
                      InnerNavComponent ],
      imports: [NgbModule,
                SharedModule,
                RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InnerNavItemComponent);
    component = fixture.componentInstance;
    component.navItem = navItem;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
