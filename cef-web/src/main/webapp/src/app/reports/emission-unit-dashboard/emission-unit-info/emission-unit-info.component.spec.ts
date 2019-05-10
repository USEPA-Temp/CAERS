import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionUnitInfoComponent } from './emission-unit-info.component';

describe('EmissionUnitInfoComponent', () => {
  let component: EmissionUnitInfoComponent;
  let fixture: ComponentFixture<EmissionUnitInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionUnitInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionUnitInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
