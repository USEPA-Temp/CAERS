import { TestBed } from '@angular/core/testing';

import { UserContextService } from './user-context.service';

describe('UserContextService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserContextService = TestBed.get(UserContextService);
    expect(service).toBeTruthy();
  });
});
