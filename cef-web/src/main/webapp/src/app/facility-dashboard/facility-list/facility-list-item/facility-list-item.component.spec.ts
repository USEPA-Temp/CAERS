import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityListItemComponent } from './facility-list-item.component';

describe('FacilityListItemComponent', () => {
  let component: FacilityListItemComponent;
  let fixture: ComponentFixture<FacilityListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
