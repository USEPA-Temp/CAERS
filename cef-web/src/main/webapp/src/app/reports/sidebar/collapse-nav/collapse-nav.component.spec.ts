import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CollapseNavComponent } from './collapse-nav.component';

describe('CollapseNavComponent', () => {
  let component: CollapseNavComponent;
  let fixture: ComponentFixture<CollapseNavComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CollapseNavComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CollapseNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
