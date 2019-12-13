import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateControlDeviceComponent } from './create-control-device.component';

describe('CreateControlDeviceComponent', () => {
  let component: CreateControlDeviceComponent;
  let fixture: ComponentFixture<CreateControlDeviceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateControlDeviceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateControlDeviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
