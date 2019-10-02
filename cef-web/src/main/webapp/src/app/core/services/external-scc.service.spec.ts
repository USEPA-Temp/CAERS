import { TestBed } from '@angular/core/testing';

import { ExternalSccService } from './external-scc.service';

describe('ExternalSccService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ExternalSccService = TestBed.get(ExternalSccService);
    expect(service).toBeTruthy();
  });
});
