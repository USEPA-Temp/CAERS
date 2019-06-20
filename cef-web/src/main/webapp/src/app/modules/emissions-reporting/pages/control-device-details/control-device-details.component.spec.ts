import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlDeviceDetailsComponent } from './control-device-details.component';

describe('ControlDeviceDetailsComponent', () => {
  let component: ControlDeviceDetailsComponent;
  let fixture: ComponentFixture<ControlDeviceDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlDeviceDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlDeviceDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
