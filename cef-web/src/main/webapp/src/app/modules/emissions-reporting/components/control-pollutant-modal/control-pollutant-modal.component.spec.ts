import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlPollutantModalComponent } from './control-pollutant-modal.component';

describe('ControlPollutantModalComponent', () => {
  let component: ControlPollutantModalComponent;
  let fixture: ComponentFixture<ControlPollutantModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlPollutantModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPollutantModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
