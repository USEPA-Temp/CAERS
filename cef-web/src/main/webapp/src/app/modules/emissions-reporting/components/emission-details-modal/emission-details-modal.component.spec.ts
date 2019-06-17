import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionDetailsModalComponent } from './emission-details-modal.component';

describe('EmissionDetailsModalComponent', () => {
  let component: EmissionDetailsModalComponent;
  let fixture: ComponentFixture<EmissionDetailsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionDetailsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionDetailsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
