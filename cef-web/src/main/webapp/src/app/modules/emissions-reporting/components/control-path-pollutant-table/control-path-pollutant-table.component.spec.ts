import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlPathPollutantTableComponent } from './control-path-pollutant-table.component';

describe('ControlPathPollutantTableComponent', () => {
  let component: ControlPathPollutantTableComponent;
  let fixture: ComponentFixture<ControlPathPollutantTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlPathPollutantTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPathPollutantTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
