import { TestBed } from '@angular/core/testing';

import { AdminPropertyService } from './admin-property.service';

describe('AdminPropertyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AdminPropertyService = TestBed.get(AdminPropertyService);
    expect(service).toBeTruthy();
  });
});
