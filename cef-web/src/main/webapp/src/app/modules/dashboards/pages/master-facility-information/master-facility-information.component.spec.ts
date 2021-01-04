import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterFacilityInformationComponent } from './master-facility-information.component';

describe('MasterFacilityInformationComponent', () => {
  let component: MasterFacilityInformationComponent;
  let fixture: ComponentFixture<MasterFacilityInformationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasterFacilityInformationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterFacilityInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
