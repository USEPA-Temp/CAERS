import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidationReminderComponent } from './validation-reminder.component';

describe('ValidationReminderComponent', () => {
  let component: ValidationReminderComponent;
  let fixture: ComponentFixture<ValidationReminderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ValidationReminderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ValidationReminderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
