import { TestBed } from '@angular/core/testing';

import { EmissionsProcessService } from './emissions-process.service';

describe('EmissionsProcessService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EmissionsProcessService = TestBed.get(EmissionsProcessService);
    expect(service).toBeTruthy();
  });
});
