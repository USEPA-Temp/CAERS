import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RejectSubmissionModalComponent } from './reject-submission-modal.component';

describe('RejectSubmissionModalComponent', () => {
  let component: RejectSubmissionModalComponent;
  let fixture: ComponentFixture<RejectSubmissionModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RejectSubmissionModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RejectSubmissionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
