import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsUnitTableComponent } from './emissions-unit-table.component';

describe('EmissionsUnitTableComponent', () => {
  let component: EmissionsUnitTableComponent;
  let fixture: ComponentFixture<EmissionsUnitTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionsUnitTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsUnitTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
