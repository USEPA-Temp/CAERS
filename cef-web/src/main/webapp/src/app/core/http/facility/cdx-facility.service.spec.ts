import { TestBed } from '@angular/core/testing';

import { CdxFacilityService } from './cdx-facility.service';
import { HttpClientModule } from '@angular/common/http';

describe('FacilityService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports: [
                                                             HttpClientModule
                                                             ]}));

  it('should be created', () => {
    const service: CdxFacilityService = TestBed.get(CdxFacilityService);
    expect(service).toBeTruthy();
  });
});
