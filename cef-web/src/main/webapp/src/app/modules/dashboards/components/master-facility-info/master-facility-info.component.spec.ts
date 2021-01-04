import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterFacilityInfoComponent } from './master-facility-info.component';

describe('MasterFacilityInfoComponent', () => {
  let component: MasterFacilityInfoComponent;
  let fixture: ComponentFixture<MasterFacilityInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasterFacilityInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterFacilityInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
