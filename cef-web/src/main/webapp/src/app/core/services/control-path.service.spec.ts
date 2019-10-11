import { TestBed } from '@angular/core/testing';

import { ControlPathService } from './control-path.service';

describe('ControlPathService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ControlPathService = TestBed.get(ControlPathService);
    expect(service).toBeTruthy();
  });
});
