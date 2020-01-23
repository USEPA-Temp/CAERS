import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditControlPathInfoPanelComponent } from './edit-control-path-info-panel.component';

describe('EditControlPathInfoPanelComponent', () => {
  let component: EditControlPathInfoPanelComponent;
  let fixture: ComponentFixture<EditControlPathInfoPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditControlPathInfoPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditControlPathInfoPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
