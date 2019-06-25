import { TestBed } from '@angular/core/testing';

import { ControlAssignmentService } from './control-assignment.service';

describe('ControlAssignmentService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ControlAssignmentService = TestBed.get(ControlAssignmentService);
    expect(service).toBeTruthy();
  });
});
