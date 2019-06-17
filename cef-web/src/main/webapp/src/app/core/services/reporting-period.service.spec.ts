import { TestBed } from '@angular/core/testing';

import { ReportingPeriodService } from './reporting-period.service';

describe('ReportingPeriodService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ReportingPeriodService = TestBed.get(ReportingPeriodService);
    expect(service).toBeTruthy();
  });
});
