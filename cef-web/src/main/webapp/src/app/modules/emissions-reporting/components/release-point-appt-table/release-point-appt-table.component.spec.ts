import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReleasePointApptTableComponent } from './release-point-appt-table.component';

describe('ReleasePointApptTableComponent', () => {
  let component: ReleasePointApptTableComponent;
  let fixture: ComponentFixture<ReleasePointApptTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReleasePointApptTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleasePointApptTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
