import { TestBed } from '@angular/core/testing';

import { FacilitySiteResolverService } from './facility-site-resolver.service';

describe('FacilitySiteResolverService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FacilitySiteResolverService = TestBed.get(FacilitySiteResolverService);
    expect(service).toBeTruthy();
  });
});
