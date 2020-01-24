import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlPathsTableComponent } from './control-paths-table.component';

describe('ControlPathsTableComponent', () => {
  let component: ControlPathsTableComponent;
  let fixture: ComponentFixture<ControlPathsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlPathsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPathsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
