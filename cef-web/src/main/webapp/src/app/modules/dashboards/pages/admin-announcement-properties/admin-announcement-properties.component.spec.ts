import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAnnouncementPropertiesComponent } from './admin-announcement-properties.component';

describe('AdminAnnouncementPropertiesComponent', () => {
  let component: AdminAnnouncementPropertiesComponent;
  let fixture: ComponentFixture<AdminAnnouncementPropertiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminAnnouncementPropertiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminAnnouncementPropertiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
