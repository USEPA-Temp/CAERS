import { TestBed } from '@angular/core/testing';

import { SubmissionReviewServiceService } from './submission-review-service.service';

describe('SubmissionReviewServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SubmissionReviewServiceService = TestBed.get(SubmissionReviewServiceService);
    expect(service).toBeTruthy();
  });
});
