import { TestBed } from '@angular/core/testing';

import { EmissionService } from './emission.service';

describe('EmissionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EmissionService = TestBed.get(EmissionService);
    expect(service).toBeTruthy();
  });
});
