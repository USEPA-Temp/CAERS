/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionUnitInfoComponent } from './emission-unit-info.component';

describe('EmissionUnitInfoComponent', () => {
  let component: EmissionUnitInfoComponent;
  let fixture: ComponentFixture<EmissionUnitInfoComponent>;
  const emissionUnit = {
    id : 1,
    unitId : '001',
    description : 'Boiler 001',
    unitType : {
      code : '100',
      description : 'Boiler'
    },
    processes : [
      {
        id : 1,
        description : 'Process 007',
        sourceClassificationCode : '10320587',
        releasePointAppts : [
          {
            id : 1,
            percent : 100,
            releasePoint : {
              id: 1,
              description: 'Release Point 002',
              typeCode: 'Vertical'
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
