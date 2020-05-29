import { TestBed, async, inject } from '@angular/core/testing';

import { ReviewerAuthGuard } from './reviewer-auth.guard';

describe('ReviewerAuthGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReviewerAuthGuard]
    });
  });

  it('should ...', inject([ReviewerAuthGuard], (guard: ReviewerAuthGuard) => {
    expect(guard).toBeTruthy();
  }));
});
