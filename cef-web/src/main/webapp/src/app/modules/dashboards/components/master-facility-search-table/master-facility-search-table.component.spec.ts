import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterFacilitySearchTableComponent } from './master-facility-search-table.component';

describe('MasterFacilitySearchTableComponent', () => {
  let component: MasterFacilitySearchTableComponent;
  let fixture: ComponentFixture<MasterFacilitySearchTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasterFacilitySearchTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterFacilitySearchTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
