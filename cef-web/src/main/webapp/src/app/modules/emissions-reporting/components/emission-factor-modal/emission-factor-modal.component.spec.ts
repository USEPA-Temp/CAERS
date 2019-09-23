import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionFactorModalComponent } from './emission-factor-modal.component';

describe('EmissionFactorModalComponent', () => {
  let component: EmissionFactorModalComponent;
  let fixture: ComponentFixture<EmissionFactorModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionFactorModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionFactorModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
