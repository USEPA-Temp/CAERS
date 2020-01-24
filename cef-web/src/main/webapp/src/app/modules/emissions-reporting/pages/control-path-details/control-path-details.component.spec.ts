import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlPathDetailsComponent } from './control-path-details.component';

describe('ControlPathDetailsComponent', () => {
  let component: ControlPathDetailsComponent;
  let fixture: ComponentFixture<ControlPathDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlPathDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPathDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
