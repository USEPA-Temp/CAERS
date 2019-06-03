import { TestBed } from '@angular/core/testing';

import { FacilityService } from './facility.service';
import { HttpClientModule }    from '@angular/common/http';

describe('FacilityService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports: [
                                                             HttpClientModule
                                                             ]}));

  it('should be created', () => {
    const service: FacilityService = TestBed.get(FacilityService);
    expect(service).toBeTruthy();
  });
});
