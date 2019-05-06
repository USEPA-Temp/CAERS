import { TestBed } from '@angular/core/testing';

import { FacilityContextService } from './facility-context.service';

describe('FacilityContextService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FacilityContextService = TestBed.get(FacilityContextService);
    expect(service).toBeTruthy();
  });
});
