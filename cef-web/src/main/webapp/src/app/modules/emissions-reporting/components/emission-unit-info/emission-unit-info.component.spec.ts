import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionUnitInfoComponent } from './emission-unit-info.component';

describe('EmissionUnitInfoComponent', () => {
  let component: EmissionUnitInfoComponent;
  let fixture: ComponentFixture<EmissionUnitInfoComponent>;
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
        "releasePointAppts" : [
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
      declarations: [ EmissionUnitInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionUnitInfoComponent);
    component = fixture.componentInstance;
    component.emissionUnit = emissionUnit;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
