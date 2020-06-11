import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewerNavComponent } from './reviewer-nav.component';

describe('ReviewerNavComponent', () => {
  let component: ReviewerNavComponent;
  let fixture: ComponentFixture<ReviewerNavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewerNavComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewerNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
