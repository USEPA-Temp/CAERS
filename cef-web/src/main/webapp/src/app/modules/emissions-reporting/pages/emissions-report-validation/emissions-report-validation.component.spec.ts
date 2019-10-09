import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsReportValidationComponent } from './emissions-report-validation.component';

describe('EmissionsReportValidationComponent', () => {
  let component: EmissionsReportValidationComponent;
  let fixture: ComponentFixture<EmissionsReportValidationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionsReportValidationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsReportValidationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
