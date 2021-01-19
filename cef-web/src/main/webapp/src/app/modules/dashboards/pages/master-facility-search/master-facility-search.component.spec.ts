import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterFacilitySearchComponent } from './master-facility-search.component';

describe('MasterFacilitySearchComponent', () => {
  let component: MasterFacilitySearchComponent;
  let fixture: ComponentFixture<MasterFacilitySearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasterFacilitySearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterFacilitySearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
