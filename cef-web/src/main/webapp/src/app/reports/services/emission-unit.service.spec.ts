import { TestBed } from '@angular/core/testing';

import { EmissionUnitService } from './emission-unit.service';

describe('EmissionUnitService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EmissionUnitService = TestBed.get(EmissionUnitService);
    expect(service).toBeTruthy();
  });
});
