import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FileAttachmentModalComponent } from './file-attachment-modal.component';


describe('SubmissionReviewModalComponent', () => {
  let component: FileAttachmentModalComponent;
  let fixture: ComponentFixture<FileAttachmentModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileAttachmentModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileAttachmentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
