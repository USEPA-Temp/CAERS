import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityInfoComponent } from './facility-info.component';


describe('FacilityInfoComponent', () => {
  let component: FacilityInfoComponent;
  let fixture: ComponentFixture<FacilityInfoComponent>;

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
  
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityInfoComponent);
    component = fixture.componentInstance;
    component.facility=facility;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
