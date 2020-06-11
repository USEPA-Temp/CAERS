import { TestBed } from '@angular/core/testing';

import { EisDataService } from './eis-data.service';

describe('EisDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EisDataService = TestBed.get(EisDataService);
    expect(service).toBeTruthy();
  });
});
