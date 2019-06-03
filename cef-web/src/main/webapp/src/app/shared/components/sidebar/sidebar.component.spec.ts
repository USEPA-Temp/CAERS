import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarComponent } from './sidebar.component';
import { CollapseNavComponent } from 'src/app/shared/components/collapse-nav/collapse-nav.component';
import { SidebarInnerNavComponent } from 'src/app/shared/components/sidebar-inner-nav/sidebar-inner-nav.component';
import { SidebarInnerNavItemComponent } from 'src/app/shared/components/sidebar-inner-nav-item/sidebar-inner-nav-item.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/shared/shared.module';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';


describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;
  let facility = {
      cdxFacilityId: 123,
      epaRegistryId: "123",
      programId: "123",
      facilityName: "test-facility",
      address: "123 elm st",
      address2: "",
      city: "Fairfax",
      state: "Virginia",
      county: "US",
      zipCode: "22033"
  };
  let report = {
      id: 1,
      facilityId: "123",
      status: "InProgress",
      year: 2019
  };
  

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SidebarComponent, 
                      CollapseNavComponent,
                      InnerNavComponent,
                      InnerNavItemComponent ],
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
