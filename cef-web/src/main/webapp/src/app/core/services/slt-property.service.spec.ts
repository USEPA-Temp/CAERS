import { TestBed } from '@angular/core/testing';

import { SltPropertyService } from './slt-property.service';

describe('SltPropertyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SltPropertyService = TestBed.get(SltPropertyService);
    expect(service).toBeTruthy();
  });
});
