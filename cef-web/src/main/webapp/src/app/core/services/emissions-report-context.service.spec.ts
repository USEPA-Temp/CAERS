import { TestBed } from '@angular/core/testing';

import { EmissionsReportContextService } from './emissions-report-context.service';

describe('EmissionsReportContextService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EmissionsReportContextService = TestBed.get(EmissionsReportContextService);
    expect(service).toBeTruthy();
  });
});
