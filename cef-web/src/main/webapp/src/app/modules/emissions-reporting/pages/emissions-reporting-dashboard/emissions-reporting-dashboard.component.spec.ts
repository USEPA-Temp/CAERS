import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsReportingDashboardComponent } from './emissions-reporting-dashboard.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';

describe('ReportsDashboardComponent', () => {
  let component: EmissionsReportingDashboardComponent;
  let fixture: ComponentFixture<EmissionsReportingDashboardComponent>;
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
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EmissionsReportingDashboardComponent],
      imports: [HttpClientModule, RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsReportingDashboardComponent);
    component = fixture.componentInstance;
    component.facility = facility;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
