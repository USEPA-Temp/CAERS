import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AdminUserFeedbackComponent } from './admin-user-feedback.component';

describe('AdminUserFeedbackComponent', () => {
  let component: AdminUserFeedbackComponent;
  let fixture: ComponentFixture<AdminUserFeedbackComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminUserFeedbackComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminUserFeedbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
