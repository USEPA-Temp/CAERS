import { TestBed } from '@angular/core/testing';

import { ReportAttachmentService } from './report-attachment.service';

describe('ReportService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ReportAttachmentService = TestBed.get(ReportAttachmentService);
    expect(service).toBeTruthy();
  });
});
