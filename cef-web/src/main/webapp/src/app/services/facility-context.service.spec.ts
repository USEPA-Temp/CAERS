import { TestBed } from '@angular/core/testing';

import { FacilityContextService } from './facility-context.service';
import { HttpClientModule }    from '@angular/common/http';

describe('FacilityContextService', () => {
  beforeEach(() => TestBed.configureTestingModule({ imports: [
                                                              HttpClientModule
                                                           ]}));

  it('should be created', () => {
    const service: FacilityContextService = TestBed.get(FacilityContextService);
    expect(service).toBeTruthy();
  });
});
