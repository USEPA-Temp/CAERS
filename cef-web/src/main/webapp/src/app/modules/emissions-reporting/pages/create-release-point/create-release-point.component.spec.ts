import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateReleasePointComponent } from './create-release-point.component';

describe('CreateReleasePointComponent', () => {
  let component: CreateReleasePointComponent;
  let fixture: ComponentFixture<CreateReleasePointComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateReleasePointComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateReleasePointComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
