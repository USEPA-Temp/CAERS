import { TestBed } from '@angular/core/testing';

import { UserFacilityAssociationService } from './user-facility-association.service';

describe('UserFacilityAssociationService', () => {
  let service: UserFacilityAssociationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserFacilityAssociationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
