import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReleasePointDetailsComponent } from './release-point-details.component';

describe('ReleasePointDetailsComponent', () => {
  let component: ReleasePointDetailsComponent;
  let fixture: ComponentFixture<ReleasePointDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReleasePointDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleasePointDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
