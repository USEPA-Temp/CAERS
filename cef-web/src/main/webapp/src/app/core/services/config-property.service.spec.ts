import { TestBed } from '@angular/core/testing';

import { ConfigPropertyService } from './config-property.service';

describe('ConfigPropertyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ConfigPropertyService = TestBed.get(ConfigPropertyService);
    expect(service).toBeTruthy();
  });
});
