import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditMasterFacilityInfoComponent } from './edit-master-facility-info.component';

describe('EditMasterFacilityInfoComponent', () => {
  let component: EditMasterFacilityInfoComponent;
  let fixture: ComponentFixture<EditMasterFacilityInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditMasterFacilityInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditMasterFacilityInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
