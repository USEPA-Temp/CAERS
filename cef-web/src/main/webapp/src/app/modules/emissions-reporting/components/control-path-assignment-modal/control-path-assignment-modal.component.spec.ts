import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlPathAssignmentModalComponent } from './control-path-assignment-modal.component';

describe('ControlPathAssignmentModalComponent', () => {
  let component: ControlPathAssignmentModalComponent;
  let fixture: ComponentFixture<ControlPathAssignmentModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlPathAssignmentModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPathAssignmentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
