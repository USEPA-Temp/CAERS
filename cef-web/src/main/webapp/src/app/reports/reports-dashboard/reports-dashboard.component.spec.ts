import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportsDashboardComponent } from './reports-dashboard.component';

describe('ReportsDashboardComponent', () => {
  let component: ReportsDashboardComponent;
  let fixture: ComponentFixture<ReportsDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportsDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportsDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
