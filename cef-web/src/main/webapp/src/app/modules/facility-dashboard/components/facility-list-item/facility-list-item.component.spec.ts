import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityListItemComponent } from './facility-list-item.component';
import { SharedModule} from 'src/app/shared/shared.module';
import { RouterTestingModule } from '@angular/router/testing';

describe('FacilityListItemComponent', () => {
  let component: FacilityListItemComponent;
  let fixture: ComponentFixture<FacilityListItemComponent>;
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
      declarations: [ FacilityListItemComponent ],
      imports: [SharedModule, RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityListItemComponent);
    component = fixture.componentInstance;
    component.facility=facility;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
