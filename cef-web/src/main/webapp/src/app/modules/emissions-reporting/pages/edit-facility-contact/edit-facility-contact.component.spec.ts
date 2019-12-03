import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { EditFacilityContactComponent } from './edit-facility-contact.component';

describe('EditFacilityContactComponent', () => {
  let component: EditFacilityContactComponent;
  let fixture: ComponentFixture<EditFacilityContactComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditFacilityContactComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditFacilityContactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
