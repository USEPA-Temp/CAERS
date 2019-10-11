import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlPathPanelComponent } from './control-path-panel.component';

describe('ControlPathPanelComponent', () => {
  let component: ControlPathPanelComponent;
  let fixture: ComponentFixture<ControlPathPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlPathPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPathPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
