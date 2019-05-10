import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionUnitDashboardComponent } from './emission-unit-dashboard.component';

describe('EmissionUnitDashboardComponent', () => {
  let component: EmissionUnitDashboardComponent;
  let fixture: ComponentFixture<EmissionUnitDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionUnitDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionUnitDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
