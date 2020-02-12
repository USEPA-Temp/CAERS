import { TestBed } from '@angular/core/testing';

import { ReportDownloadService } from './report-download.service';

describe('ReportDownloadService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ReportDownloadService = TestBed.get(ReportDownloadService);
    expect(service).toBeTruthy();
  });
});
