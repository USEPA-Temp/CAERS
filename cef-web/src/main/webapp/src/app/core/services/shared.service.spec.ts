import { TestBed } from '@angular/core/testing';
import { SharedService } from "src/app/core/services/shared.service";


describe('SharedService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SharedService = TestBed.get(SharedService);
    expect(service).toBeTruthy();
  });
});
