import { TestBed } from '@angular/core/testing';

import { RouteContextService } from './route-context.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('RouteContextService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports: [
                                                             RouterTestingModule
                                                             ]}));

  it('should be created', () => {
    const service: RouteContextService = TestBed.get(RouteContextService);
    expect(service).toBeTruthy();
  });
});
