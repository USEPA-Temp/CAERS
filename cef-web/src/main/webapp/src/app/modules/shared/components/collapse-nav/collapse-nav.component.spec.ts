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
