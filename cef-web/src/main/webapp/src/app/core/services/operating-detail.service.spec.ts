import { TestBed } from '@angular/core/testing';

import { OperatingDetailService } from './operating-detail.service';

describe('OperatingDetailService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OperatingDetailService = TestBed.get(OperatingDetailService);
    expect(service).toBeTruthy();
  });
});
