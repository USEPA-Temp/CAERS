import { TestBed } from '@angular/core/testing';

import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { HttpClientModule }    from '@angular/common/http';

describe('ReportService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports: [
                                                             HttpClientModule
                                                             ]}));

  it('should be created', () => {
    const service: EmissionsReportingService = TestBed.get(EmissionsReportingService);
    expect(service).toBeTruthy();
  });
});
