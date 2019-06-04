import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReleasePointTableComponent } from './release-point-table.component';

describe('ReleasePointTableComponent', () => {
  let component: ReleasePointTableComponent;
  let fixture: ComponentFixture<ReleasePointTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReleasePointTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleasePointTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
