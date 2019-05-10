import { TestBed } from '@angular/core/testing';

import { UserContextService } from './user-context.service';
import { HttpClientModule } from "@angular/common/http";

describe('UserContextService', () => {
  beforeEach(() => TestBed.configureTestingModule({imports: [
                                                             HttpClientModule,
                                                             ]}));

  it('should be created', () => {
    const service: UserContextService = TestBed.get(UserContextService);
    expect(service).toBeTruthy();
  });
});
