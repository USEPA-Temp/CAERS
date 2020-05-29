import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EisNotificationsComponent } from './eis-notifications.component';

describe('EisNotificationsComponent', () => {
  let component: EisNotificationsComponent;
  let fixture: ComponentFixture<EisNotificationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EisNotificationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EisNotificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
