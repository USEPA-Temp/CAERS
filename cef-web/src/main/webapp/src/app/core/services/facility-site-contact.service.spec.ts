import { TestBed } from '@angular/core/testing';

import { FacilitySiteContactService } from './facility-site-contact.service';

describe('FacilitySiteContactService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FacilitySiteContactService = TestBed.get(FacilitySiteContactService);
    expect(service).toBeTruthy();
  });
});
