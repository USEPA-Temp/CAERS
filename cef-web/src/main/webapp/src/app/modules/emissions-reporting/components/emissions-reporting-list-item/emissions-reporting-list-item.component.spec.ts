import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsReportingListItemComponent } from './emissions-reporting-list-item.component';
import { HttpClientModule } from '@angular/common/http';
import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { EmissionsReportingListButtonsComponent } from 'src/app/modules/emissions-reporting/components/emissions-reporting-list-buttons/emissions-reporting-list-buttons.component';

describe('EmissionsReportingListItemComponent', () => {
  let component: EmissionsReportingListItemComponent;
  let fixture: ComponentFixture<EmissionsReportingListItemComponent>;
  let report = new EmissionsReport();
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionsReportingListItemComponent , EmissionsReportingListButtonsComponent],
      imports : [HttpClientModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsReportingListItemComponent);
    component = fixture.componentInstance;
    component.report=report;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
