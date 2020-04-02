import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityDashboardComponent } from './facility-dashboard.component';
import { NotificationListComponent } from 'src/app/shared/components/notification-list/notification-list.component';
import { FacilityListComponent} from 'src/app/modules/dashboards/components/facility-list/facility-list.component';
import { FacilityListItemComponent } from 'src/app/modules/dashboards/components/facility-list-item/facility-list-item.component';

import { SharedModule } from 'src/app/modules/shared/shared.module';
import { RouterTestingModule } from '@angular/router/testing';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';

import { HttpClientModule } from '@angular/common/http';

describe('FacilityDashboardComponent', () => {
  let component: FacilityDashboardComponent;
  let fixture: ComponentFixture<FacilityDashboardComponent>;
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
  const facilities: CdxFacility[] = [facility];
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityDashboardComponent,
                      NotificationListComponent,
                      FacilityListComponent,
                      FacilityListItemComponent
                      ],
      imports: [SharedModule, RouterTestingModule, HttpClientModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityDashboardComponent);
    component = fixture.componentInstance;
    component.facilities = facilities;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
