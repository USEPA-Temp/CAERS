import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlDevicesTableComponent } from './control-devices-table.component';

describe('ControlDevicesTableComponent', () => {
  let component: ControlDevicesTableComponent;
  let fixture: ComponentFixture<ControlDevicesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlDevicesTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlDevicesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
