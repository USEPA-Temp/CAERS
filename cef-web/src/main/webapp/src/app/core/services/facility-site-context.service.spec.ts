import { TestBed } from '@angular/core/testing';

import { FacilitySiteContextService } from './facility-site-context.service';

describe('FacilitySiteContextService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FacilitySiteContextService = TestBed.get(FacilitySiteContextService);
    expect(service).toBeTruthy();
  });
});
