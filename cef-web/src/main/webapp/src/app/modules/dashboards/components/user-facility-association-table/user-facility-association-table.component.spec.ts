import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFacilityAssociationTableComponent } from './user-facility-association-table.component';

describe('UserFacilityAssociationTableComponent', () => {
  let component: UserFacilityAssociationTableComponent;
  let fixture: ComponentFixture<UserFacilityAssociationTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserFacilityAssociationTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFacilityAssociationTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
