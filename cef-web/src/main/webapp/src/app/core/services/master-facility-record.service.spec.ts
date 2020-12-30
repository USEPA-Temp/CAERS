import { TestBed } from '@angular/core/testing';

import { MasterFacilityRecordService } from './master-facility-record.service';

describe('MasterFacilityRecordService', () => {
  let service: MasterFacilityRecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MasterFacilityRecordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
