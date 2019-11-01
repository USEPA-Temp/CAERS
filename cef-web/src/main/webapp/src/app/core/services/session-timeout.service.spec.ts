import { TestBed } from '@angular/core/testing';

import { SessionTimeoutService } from './session-timeout.service';

describe('SessionTimeoutService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SessionTimeoutService = TestBed.get(SessionTimeoutService);
    expect(service).toBeTruthy();
  });
});
