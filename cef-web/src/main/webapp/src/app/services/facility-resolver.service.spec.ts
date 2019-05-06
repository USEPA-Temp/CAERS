import { TestBed } from '@angular/core/testing';

import { FacilityResolverService } from './facility-resolver.service';

describe('FacilityResolverService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FacilityResolverService = TestBed.get(FacilityResolverService);
    expect(service).toBeTruthy();
  });
});
