import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlDevicePathsTableComponent } from './control-device-paths-table.component';

describe('ControlDevicePathsTableComponent', () => {
  let component: ControlDevicePathsTableComponent;
  let fixture: ComponentFixture<ControlDevicePathsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlDevicePathsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlDevicePathsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
