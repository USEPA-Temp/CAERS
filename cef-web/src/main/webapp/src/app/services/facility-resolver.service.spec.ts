import { TestBed } from '@angular/core/testing';

import { FacilityResolverService } from './facility-resolver.service';
import { HttpClientModule }    from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';

describe('FacilityResolverService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports: [
                                                             HttpClientModule,
                                                             RouterTestingModule
                                                             ]}));

  it('should be created', () => {
    const service: FacilityResolverService = TestBed.get(FacilityResolverService);
    expect(service).toBeTruthy();
  });
});
