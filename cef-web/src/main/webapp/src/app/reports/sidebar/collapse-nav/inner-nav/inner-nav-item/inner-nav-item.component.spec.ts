import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InnerNavItemComponent } from './inner-nav-item.component';

describe('InnerNavItemComponent', () => {
  let component: InnerNavItemComponent;
  let fixture: ComponentFixture<InnerNavItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InnerNavItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InnerNavItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
