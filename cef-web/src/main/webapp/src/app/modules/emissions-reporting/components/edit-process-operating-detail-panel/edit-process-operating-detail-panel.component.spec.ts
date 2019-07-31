import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProcessOperatingDetailPanelComponent } from './edit-process-operating-detail-panel.component';

describe('EditProcessOperatingDetailPanelComponent', () => {
  let component: EditProcessOperatingDetailPanelComponent;
  let fixture: ComponentFixture<EditProcessOperatingDetailPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditProcessOperatingDetailPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditProcessOperatingDetailPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
