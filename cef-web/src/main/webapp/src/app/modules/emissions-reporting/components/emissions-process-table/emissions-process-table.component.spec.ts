import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionsProcessTableComponent } from './emissions-process-table.component';

describe('EmissionsProcessTableComponent', () => {
  let component: EmissionsProcessTableComponent;
  let fixture: ComponentFixture<EmissionsProcessTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionsProcessTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionsProcessTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
