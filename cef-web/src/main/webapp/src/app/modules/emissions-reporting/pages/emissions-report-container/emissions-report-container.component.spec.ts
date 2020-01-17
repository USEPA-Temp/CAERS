import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsReportContainerComponent } from './emissions-report-container.component';

describe('EmissionsReportContainerComponent', () => {
  let component: EmissionsReportContainerComponent;
  let fixture: ComponentFixture<EmissionsReportContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionsReportContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsReportContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
