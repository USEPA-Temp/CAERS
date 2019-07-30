import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProcessReportingPeriodPanelComponent } from './edit-process-reporting-period-panel.component';

describe('EditProcessReportingPeriodPanelComponent', () => {
  let component: EditProcessReportingPeriodPanelComponent;
  let fixture: ComponentFixture<EditProcessReportingPeriodPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditProcessReportingPeriodPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditProcessReportingPeriodPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
