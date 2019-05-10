import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionUnitDashboardComponent } from './emission-unit-dashboard.component';
import { EmissionUnitInfoComponent } from './emission-unit-info/emission-unit-info.component';
import { EmissionProcessListComponent } from './emission-process-list/emission-process-list.component';
import { StepProgressComponent } from 'src/app/reports/components/step-progress/step-progress.component';

import { RouterTestingModule } from '@angular/router/testing';

import { HttpClientModule }    from '@angular/common/http';

describe('EmissionUnitDashboardComponent', () => {
  let component: EmissionUnitDashboardComponent;
  let fixture: ComponentFixture<EmissionUnitDashboardComponent>;
  let emissionUnit = {
    "id" : 1,
    "unitId" : "001",
    "description" : "Boiler 001",
    "unitType" : {
      "code" : "100",
      "description" : "Boiler"
    },
    "processes" : [
      {
        "id" : 1,
        "description" : "Process 007",
        "sourceClassificationCode" : "10320587",
        "releasePoints" : [
          {
            "id" : 1,
            "percent" : 100,
            "releasePoint" : {
              "id": 1,
              "description": "Release Point 002",
              "typeCode": "Vertical"
            }
          }
        ]
      }
    ]
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionUnitDashboardComponent,
                      EmissionUnitInfoComponent,
                      EmissionProcessListComponent,
                      StepProgressComponent],
      imports: [RouterTestingModule, HttpClientModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionUnitDashboardComponent);
    component = fixture.componentInstance;
    component.emissionUnit = emissionUnit;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
