import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportAttachmentTableComponent } from './report-attachment-table.component';

describe('ReportAttachmentTableComponent', () => {
  let component: ReportAttachmentTableComponent;
  let fixture: ComponentFixture<ReportAttachmentTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportAttachmentTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportAttachmentTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
