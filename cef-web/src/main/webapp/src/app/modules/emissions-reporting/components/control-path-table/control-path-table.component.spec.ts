import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlPathTableComponent } from './control-path-table.component';

describe('ControlPathTableComponent', () => {
  let component: ControlPathTableComponent;
  let fixture: ComponentFixture<ControlPathTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlPathTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPathTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
