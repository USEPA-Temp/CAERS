import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionUnitListComponent } from './emission-unit-list.component';

describe('EmissionUnitListComponent', () => {
  let component: EmissionUnitListComponent;
  let fixture: ComponentFixture<EmissionUnitListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionUnitListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionUnitListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
