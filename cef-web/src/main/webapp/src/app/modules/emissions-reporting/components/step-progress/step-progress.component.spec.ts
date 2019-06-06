import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StepProgressComponent } from './step-progress.component';

describe('StepProgressComponent', () => {
  let component: StepProgressComponent;
  let fixture: ComponentFixture<StepProgressComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StepProgressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StepProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
