import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { EditControlDeviceInfoPanelComponent } from './edit-control-device-info-panel.component';

describe('EditControlDeviceInfoPanelComponent', () => {
  let component: EditControlDeviceInfoPanelComponent;
  let fixture: ComponentFixture<EditControlDeviceInfoPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditControlDeviceInfoPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditControlDeviceInfoPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
