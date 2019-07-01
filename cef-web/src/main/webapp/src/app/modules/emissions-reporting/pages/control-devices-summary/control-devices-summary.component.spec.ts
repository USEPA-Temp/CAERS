import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlDevicesSummaryComponent } from './control-devices-summary.component';

describe('ControlDevicesSummaryComponent', () => {
  let component: ControlDevicesSummaryComponent;
  let fixture: ComponentFixture<ControlDevicesSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlDevicesSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlDevicesSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
