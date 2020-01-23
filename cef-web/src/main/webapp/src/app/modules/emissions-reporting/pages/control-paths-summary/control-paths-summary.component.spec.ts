import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlPathsSummaryComponent } from './control-paths-summary.component';

describe('ControlPathsSummaryComponent', () => {
  let component: ControlPathsSummaryComponent;
  let fixture: ComponentFixture<ControlPathsSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlPathsSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlPathsSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
