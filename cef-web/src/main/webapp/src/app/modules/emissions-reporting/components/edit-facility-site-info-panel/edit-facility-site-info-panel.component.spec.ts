import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { EditFacilitySiteInfoPanelComponent } from './edit-facility-site-info-panel.component';

describe('EditFacilitySiteInfoPanelComponent', () => {
  let component: EditFacilitySiteInfoPanelComponent;
  let fixture: ComponentFixture<EditFacilitySiteInfoPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditFacilitySiteInfoPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditFacilitySiteInfoPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
