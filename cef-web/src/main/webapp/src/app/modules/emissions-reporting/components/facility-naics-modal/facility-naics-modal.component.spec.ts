import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityNaicsModalComponent } from './facility-naics-modal.component';

describe('FacilityNaicsModalComponent', () => {
  let component: FacilityNaicsModalComponent;
  let fixture: ComponentFixture<FacilityNaicsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityNaicsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityNaicsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
