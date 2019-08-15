import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BusyModalComponent } from './busy-modal.component';

describe('BusyModalComponent', () => {
  let component: BusyModalComponent;
  let fixture: ComponentFixture<BusyModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BusyModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BusyModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
