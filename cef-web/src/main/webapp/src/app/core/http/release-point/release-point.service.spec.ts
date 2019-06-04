import { TestBed } from '@angular/core/testing';

import { ReleasePointService } from './release-point.service';

describe('ReleasePointService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ReleasePointService = TestBed.get(ReleasePointService);
    expect(service).toBeTruthy();
  });
});
