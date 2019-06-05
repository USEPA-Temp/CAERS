import { TestBed } from '@angular/core/testing';

import { FacilitySiteService } from './facility-site.service';

describe('FacilitySiteService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FacilitySiteService = TestBed.get(FacilitySiteService);
    expect(service).toBeTruthy();
  });
});
