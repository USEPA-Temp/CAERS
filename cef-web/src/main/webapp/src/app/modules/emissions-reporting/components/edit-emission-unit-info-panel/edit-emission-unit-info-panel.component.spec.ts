import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditEmissionUnitInfoPanelComponent } from './edit-emission-unit-info-panel.component';

describe('EditEmissionUnitInfoPanelComponent', () => {
  let component: EditEmissionUnitInfoPanelComponent;
  let fixture: ComponentFixture<EditEmissionUnitInfoPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditEmissionUnitInfoPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditEmissionUnitInfoPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
