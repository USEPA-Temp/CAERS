import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MasterFacilityTableComponent } from './master-facility-table.component';

describe('MasterFacilityTableComponent', () => {
  let component: MasterFacilityTableComponent;
  let fixture: ComponentFixture<MasterFacilityTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MasterFacilityTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MasterFacilityTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
