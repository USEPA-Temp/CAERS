import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityDataReviewComponent } from './facility-data-review.component';

describe('FacilityDataReviewComponent', () => {
  let component: FacilityDataReviewComponent;
  let fixture: ComponentFixture<FacilityDataReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityDataReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityDataReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
