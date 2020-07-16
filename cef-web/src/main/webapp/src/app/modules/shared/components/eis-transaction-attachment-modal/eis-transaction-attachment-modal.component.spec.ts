import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EisTransactionAttachmentModalComponent } from './eis-transaction-attachment-modal.component';

describe('EisTransactionAttachmentModalComponent', () => {
  let component: EisTransactionAttachmentModalComponent;
  let fixture: ComponentFixture<EisTransactionAttachmentModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EisTransactionAttachmentModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EisTransactionAttachmentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
