import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReleasePointsSummaryComponent } from './release-points-summary.component';

describe('ReleasePointsSummaryComponent', () => {
  let component: ReleasePointsSummaryComponent;
  let fixture: ComponentFixture<ReleasePointsSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReleasePointsSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleasePointsSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
