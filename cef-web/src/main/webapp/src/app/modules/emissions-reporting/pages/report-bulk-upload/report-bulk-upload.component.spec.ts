import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportBulkUploadComponent } from './report-bulk-upload.component';

describe('ReportBulkUploadComponent', () => {
  let component: ReportBulkUploadComponent;
  let fixture: ComponentFixture<ReportBulkUploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportBulkUploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportBulkUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
