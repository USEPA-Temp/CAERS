import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditProcessInfoPanelComponent } from './edit-process-info-panel.component';

describe('EditProcessInfoPanelComponent', () => {
  let component: EditProcessInfoPanelComponent;
  let fixture: ComponentFixture<EditProcessInfoPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditProcessInfoPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditProcessInfoPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
