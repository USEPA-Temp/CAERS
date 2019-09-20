import { TestBed } from '@angular/core/testing';

import { EmissionFactorService } from './emission-factor.service';

describe('EmissionFactorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EmissionFactorService = TestBed.get(EmissionFactorService);
    expect(service).toBeTruthy();
  });
});
