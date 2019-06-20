import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlPollutantTableComponent } from './control-pollutant-table.component';

describe('ControlPollutantTableComponent', () => {
  let component: ControlPollutantTableComponent;
  let fixture: ComponentFixture<ControlPollutantTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlPollutantTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPollutantTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
