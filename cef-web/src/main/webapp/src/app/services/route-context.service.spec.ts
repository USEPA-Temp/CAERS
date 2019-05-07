import { TestBed } from '@angular/core/testing';

import { RouteContextService } from './route-context.service';

describe('RouteContextService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RouteContextService = TestBed.get(RouteContextService);
    expect(service).toBeTruthy();
  });
});
