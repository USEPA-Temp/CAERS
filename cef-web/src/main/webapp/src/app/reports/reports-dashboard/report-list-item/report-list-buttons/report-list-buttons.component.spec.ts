import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportListButtonsComponent } from './report-list-buttons.component';

describe('ReportListButtonsComponent', () => {
  let component: ReportListButtonsComponent;
  let fixture: ComponentFixture<ReportListButtonsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportListButtonsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportListButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
