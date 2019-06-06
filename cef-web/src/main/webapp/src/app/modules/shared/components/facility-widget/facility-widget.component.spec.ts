import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityWidgetComponent } from './facility-widget.component';

describe('FacilityWidgetComponent', () => {
  let component: FacilityWidgetComponent;
  let fixture: ComponentFixture<FacilityWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityWidgetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
