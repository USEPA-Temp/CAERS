import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionProcessListComponent } from './emission-process-list.component';

describe('EmissionProcessListComponent', () => {
  let component: EmissionProcessListComponent;
  let fixture: ComponentFixture<EmissionProcessListComponent>;
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
      declarations: [ EmissionProcessListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionProcessListComponent);
    component = fixture.componentInstance;
    component.emissionUnit = emissionUnit;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
