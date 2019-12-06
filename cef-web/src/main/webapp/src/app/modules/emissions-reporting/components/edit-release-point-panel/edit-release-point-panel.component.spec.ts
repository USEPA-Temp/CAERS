import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { EditReleasePointPanelComponent } from './edit-release-point-panel.component';

describe('EditReleasePointPanelComponent', () => {
  let component: EditReleasePointPanelComponent;
  let fixture: ComponentFixture<EditReleasePointPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditReleasePointPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditReleasePointPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
