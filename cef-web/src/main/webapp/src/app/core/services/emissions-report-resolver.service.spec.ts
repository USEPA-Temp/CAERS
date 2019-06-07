import { TestBed } from '@angular/core/testing';
import { EmissionsReportResolverService } from "src/app/core/services/emissions-report-resolver.service";


describe('ReportResolverService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EmissionsReportResolverService = TestBed.get(EmissionsReportResolverService);
    expect(service).toBeTruthy();
  });
});
