import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarInnerNavComponent } from './sidebar-inner-nav.component';
import { SidebarInnerNavItemComponent } from 'src/app/modules/shared/components/sidebar-inner-nav-item/sidebar-inner-nav-item.component';

import { SharedModule } from 'src/app/modules/shared/shared.module';

import { RouterTestingModule } from '@angular/router/testing';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';

describe('SidebarInnerNavComponent', () => {
  let component: SidebarInnerNavComponent;
  let fixture: ComponentFixture<SidebarInnerNavComponent>;
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
      declarations: [ SidebarInnerNavComponent,
                      SidebarInnerNavItemComponent ],
      imports: [NgbModule,
                SharedModule,
                RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarInnerNavComponent);
    component = fixture.componentInstance;
    component.navItems = navItems;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
