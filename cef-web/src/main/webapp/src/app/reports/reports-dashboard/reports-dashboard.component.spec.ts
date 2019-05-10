import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportsDashboardComponent } from './reports-dashboard.component';
import { ReportListItemComponent } from "src/app/reports/reports-dashboard/report-list-item/report-list-item.component";
import { HttpClientModule } from "@angular/common/http";
import { EmissionsReport } from "src/app/model/emissions-report";
import { ReportListButtonsComponent } from "src/app/reports/reports-dashboard/report-list-item/report-list-buttons/report-list-buttons.component";
import { RouterTestingModule } from '@angular/router/testing';

describe('ReportsDashboardComponent', () => {
  let component: ReportsDashboardComponent;
  let fixture: ComponentFixture<ReportsDashboardComponent>;
  let facility = {
    cdxFacilityId: 123,
    epaRegistryId: "123",
    programId: "123",
    facilityName: "test-facility",
    address: "123 elm st",
    address2: "",
    city: "Fairfax",
    state: "Virginia",
    county: "US",
    zipCode: "22033"
  };
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportsDashboardComponent, 
                      ReportListItemComponent,
                      ReportListButtonsComponent],
      imports: [HttpClientModule, RouterTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportsDashboardComponent);
    component = fixture.componentInstance;
    component.facility=facility;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
