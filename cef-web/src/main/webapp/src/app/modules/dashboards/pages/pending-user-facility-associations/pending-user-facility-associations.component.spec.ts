import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingUserFacilityAssociationsComponent } from './pending-user-facility-associations.component';

describe('PendingUserFacilityAssociationsComponent', () => {
  let component: PendingUserFacilityAssociationsComponent;
  let fixture: ComponentFixture<PendingUserFacilityAssociationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PendingUserFacilityAssociationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingUserFacilityAssociationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
