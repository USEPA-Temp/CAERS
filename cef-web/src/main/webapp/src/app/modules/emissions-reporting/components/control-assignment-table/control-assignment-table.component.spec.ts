import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlAssignmentTableComponent } from './control-assignment-table.component';

describe('ControlAssignmentTableComponent', () => {
  let component: ControlAssignmentTableComponent;
  let fixture: ComponentFixture<ControlAssignmentTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlAssignmentTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlAssignmentTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
