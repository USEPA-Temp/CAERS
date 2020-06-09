import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EisSubmissionComponent } from './eis-submission.component';

describe('EisSubmissionComponent', () => {
  let component: EisSubmissionComponent;
  let fixture: ComponentFixture<EisSubmissionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EisSubmissionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EisSubmissionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
