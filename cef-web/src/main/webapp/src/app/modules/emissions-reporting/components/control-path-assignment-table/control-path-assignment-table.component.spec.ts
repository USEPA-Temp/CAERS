import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlPathAssignmentTableComponent } from './control-path-assignment-table.component';

describe('ControlPathAssignmentTableComponent', () => {
  let component: ControlPathAssignmentTableComponent;
  let fixture: ComponentFixture<ControlPathAssignmentTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlPathAssignmentTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPathAssignmentTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
