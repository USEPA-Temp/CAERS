import { TestBed } from '@angular/core/testing';

import { ReportService } from './report.service';
import { HttpClientModule }    from '@angular/common/http';

describe('ReportService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports: [
                                                             HttpClientModule
                                                             ]}));

  it('should be created', () => {
    const service: ReportService = TestBed.get(ReportService);
    expect(service).toBeTruthy();
  });
});
