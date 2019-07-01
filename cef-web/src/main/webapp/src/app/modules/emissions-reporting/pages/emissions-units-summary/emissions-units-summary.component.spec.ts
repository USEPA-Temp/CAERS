import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsUnitsSummaryComponent } from './emissions-units-summary.component';

describe('EmissionsUnitsSummaryComponent', () => {
  let component: EmissionsUnitsSummaryComponent;
  let fixture: ComponentFixture<EmissionsUnitsSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionsUnitsSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsUnitsSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
