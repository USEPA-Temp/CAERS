import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsProcessDetailsComponent } from './emissions-process-details.component';

describe('EmissionsProcessDetailsComponent', () => {
  let component: EmissionsProcessDetailsComponent;
  let fixture: ComponentFixture<EmissionsProcessDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionsProcessDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsProcessDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
