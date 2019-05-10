import { TestBed } from '@angular/core/testing';

import { EmissionUnitService } from './emission-unit.service';
import { HttpClientModule }    from '@angular/common/http';

describe('EmissionUnitService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientModule]
  }));

  it('should be created', () => {
    const service: EmissionUnitService = TestBed.get(EmissionUnitService);
    expect(service).toBeTruthy();
  });
});
