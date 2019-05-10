import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CollapseNavComponent } from './collapse-nav.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

describe('CollapseNavComponent', () => {
  let component: CollapseNavComponent;
  let fixture: ComponentFixture<CollapseNavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollapseNavComponent ],
      imports: [NgbModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollapseNavComponent);
    component = fixture.componentInstance;
    component.collapsed=false;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
