import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CollapseIconComponent } from './collapse-icon.component';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

describe('CollapseIconComponent', () => {
  let component: CollapseIconComponent;
  let fixture: ComponentFixture<CollapseIconComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollapseIconComponent ],
      imports: [ FontAwesomeModule ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollapseIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
