import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityNaicsTableComponent } from './facility-naics-table.component';

describe('FacilityNaicsTableComponent', () => {
  let component: FacilityNaicsTableComponent;
  let fixture: ComponentFixture<FacilityNaicsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityNaicsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityNaicsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
