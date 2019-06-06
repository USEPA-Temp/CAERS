import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityListComponent } from './facility-list.component';
import { FacilityListItemComponent } from 'src/app/modules/dashboards/components/facility-list-item/facility-list-item.component';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { RouterTestingModule } from '@angular/router/testing';

describe('FacilityListComponent', () => {
  let component: FacilityListComponent;
  let fixture: ComponentFixture<FacilityListComponent>;
  let facility = {
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
  let facilities: CdxFacility[]=[facility];
  
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityListComponent, FacilityListItemComponent ],
      imports: [SharedModule, RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityListComponent);
    component = fixture.componentInstance;
    component.facilities = facilities;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
