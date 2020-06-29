import { Component, OnInit, Input, ViewChild, ElementRef, TemplateRef } from '@angular/core';
import { NgbActiveModal, NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Validators, FormBuilder, RequiredValidator } from '@angular/forms';
import bsCustomFileInput from 'bs-custom-file-input';
import { HttpEvent, HttpEventType } from '@angular/common/http';
import { EMPTY } from 'rxjs';
import { UserService } from 'src/app/core/services/user.service';
import { ReportAttachmentService } from 'src/app/core/services/report-attachment.service';
import { ReportAttachment } from 'src/app/shared/models/report-attachment';
import { SharedService } from 'src/app/core/services/shared.service';
import { ConfigPropertyService } from 'src/app/core/services/config-property.service';

interface PleaseWaitConfig {
    modal: NgbModalRef;
    message: string;
    progress: number;
    serverTicker: number;
    keepAliveTicker: number;
}

interface WorksheetError {
    worksheet: string;
    row: number;
    message: string;
    systemError: boolean;
}

@Component({
  selector: 'app-file-attachment-modal',
  templateUrl: './file-attachment-modal.component.html',
  styleUrls: ['./file-attachment-modal.component.scss']
})
export class FileAttachmentModalComponent implements OnInit {

  @Input() title: string;
  @Input() message: string;
  @Input() cancelButtonText = 'Cancel';
  @Input() confirmButtonText = 'OK';
  @Input() reportId: number;
  attachment: ReportAttachment;
  selectedFile: File = null;
  maxFileSize: number;
  acceptedMIMEtypes: string [];
  userRole: string;
  bsflags: any;
  disableButton = false;

  @ViewChild('fileAttachment', {static: true})
  fileAttachment: ElementRef;

  uploadFile: string;
  uploadFailed: boolean;
  uploadUserErrors: WorksheetError[];
  uploadSystemErrors: WorksheetError[];

  @ViewChild('PleaseWaitModal', {static: true})
  pleaseWaitTemplate: TemplateRef<any>;

  pleaseWait: PleaseWaitConfig;

  attachmentForm = this.fb.group({
    attachment: [null],
    comments: [null, [Validators.maxLength(2000)]]
  });

  constructor(public activeModal: NgbActiveModal,
              private fb: FormBuilder,
              private reportAttachmentService: ReportAttachmentService,
              private userService: UserService,
              private sharedService: SharedService,
              private propertyService: ConfigPropertyService,
              private modalService: NgbModal) {

    this.bsflags = {
      showSystemErrors: false,
      showUserErrors: true
    };
  }

  ngOnInit() {
    bsCustomFileInput.init('#file-attachment');

    this.propertyService.retrieveReportAttachmentMaxSize()
        .subscribe(result => {
            this.maxFileSize = +(result.value);
        });

    this.acceptedMIMEtypes = [
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      'application/vnd.ms-excel',
      'text/plain',
      'application/pdf',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
      'text/csv'
    ];

    this.userService.getCurrentUser().subscribe( user => {
      this.userRole = user.role;
    });

  }

  onClose() {
    this.activeModal.dismiss();
  }

  isValid() {
    return this.attachmentForm.valid;
  }

  onSubmit() {
    if (this.selectedFile === null && this.userRole !== 'Reviewer') {
      this.attachmentForm.get('attachment').setErrors({required: true});
      this.attachmentForm.get('attachment').markAsTouched();
    }

    if (!this.selectedFile && this.userRole === 'Reviewer' && this.isValid()) {

      this.activeModal.close(this.attachmentForm.value);

    } else {

      if (!this.isValid()) {
        this.attachmentForm.markAllAsTouched();
        this.attachmentForm.controls.attachment.markAsDirty();

      } else if (this.selectedFile) {

        this.pleaseWait = {
            keepAliveTicker: null,
            serverTicker: null,
            modal: this.modalService.open(this.pleaseWaitTemplate, {
                backdrop: 'static',
                size: 'lg'
            }),
            message: '',
            progress: 0
        };

        this.uploadFailed = false;
        this.uploadFile = this.selectedFile.name;

        const reportAttachment = new ReportAttachment();
        reportAttachment.reportId = this.reportId;
        Object.assign(reportAttachment, this.attachmentForm.value);

        this.sharedService.emitReportIdChange(this.reportId);
        this.reportAttachmentService.uploadAttachment(
          reportAttachment, this.selectedFile)
          .subscribe(respEvent =>
            this.onUploadEvent(respEvent),
            errorResp => this.onUploadError(errorResp),
          );
      }
    }
  }

  onUploadEvent(event: HttpEvent<any>) {

        switch (event.type) {

            case HttpEventType.Sent:
                this.pleaseWait.progress = 0;
                this.pleaseWait.message = `Uploading ${this.selectedFile.name}...`;

                this.pleaseWait.keepAliveTicker = setInterval(() => {

                    // keep alive ping
                    this.userService.getCurrentUser();

                }, 60000);

                return EMPTY;

            case HttpEventType.UploadProgress:

                const current = Math.floor(100 * (event.loaded / event.total));
                if (current < 100) {

                    if (current > this.pleaseWait.progress) {

                        this.pleaseWait.progress = current;
                    }

                } else {

                    this.pleaseWait.message = 'The server validating and saving file...';
                    this.pleaseWait.progress = 0;

                    // this could take a minute, 60000 / 100, increments
                    this.pleaseWait.keepAliveTicker = setInterval(() => {

                        if (this.pleaseWait.progress < 100) {

                            this.pleaseWait.progress += 1;
                        }

                    }, 600);

                }

                return EMPTY;

            case HttpEventType.DownloadProgress:

                this.pleaseWait.progress = 100;
                this.pleaseWait.message = 'Receiving response from server...';
                return EMPTY;

            case HttpEventType.ResponseHeader:
                return EMPTY;

            case HttpEventType.Response:

                // 200 - Success
                this.onUploadComplete();

                this.activeModal.close(event.body);

            default:

                console.log('Unknown event type', event);
        }
    }

    onUploadError(resp) {

        // we got an error response

        this.onUploadComplete();

        this.uploadUserErrors = [];
        this.uploadSystemErrors = [];

        for (let sheetError of resp.error.errors) {

            if (sheetError.systemError) {

                this.uploadSystemErrors.push(sheetError);

            } else {

                this.uploadUserErrors.push(sheetError);
            }
        }

        this.bsflags.showUserErrors = true;

        // if no user errors, show system errors
        this.bsflags.showSystemErrors = this.uploadUserErrors.length === 0;

        this.uploadFailed = true;

        // deletes selected file from the input
        this.fileAttachment.nativeElement.value = '';
        this.selectedFile = null;
        this.disableButton = true;
    }

    onUploadComplete() {

        this.pleaseWait.modal.dismiss();

        clearInterval(this.pleaseWait.serverTicker);
        this.pleaseWait.serverTicker = null;

        clearInterval(this.pleaseWait.keepAliveTicker);
        this.pleaseWait.keepAliveTicker = null;
    }

  onFileChanged(file: FileList) {
    this.bsflags.showUserErrors = false;
    this.bsflags.showSystemErrors = false;
    this.uploadUserErrors = [];
    this.uploadSystemErrors = [];

    this.selectedFile = file.length ? file.item(0) : null;

    this.disableButton = false;
    if (this.selectedFile !== null && this.userRole !== 'Reviewer') {
      this.attachmentForm.controls.attachment.setErrors(null);
    }

    if (file.item(0)) {
      this.selectedFile = file.item(0);
      this.disableButton = true;

      let fileSize = this.checkFileSize(this.selectedFile);
      let fileNameLength = this.checkFileNameLength(this.selectedFile.name);
      let fileType = this.checkFileFormat(this.selectedFile);

      if (fileNameLength || fileSize || fileType) {

        this.bsflags.showUserErrors = true;
        this.uploadFailed = true;

        this.selectedFile = null;
      } else {
        this.disableButton = false;
      }
    }

  }

  checkFileNameLength(fileName: string) {
    let nameLength = fileName.length;
    const maxLength = 255;

    if (nameLength > maxLength) {
      this.uploadUserErrors.push({ worksheet: fileName, row: null,
          message: 'File name "' + fileName + '" exceeds the maximum file name length of ' + maxLength + ' characters.',
          systemError: false});

      return true;
    }

    return false;
  }

  checkFileSize(file) {
    const maxSize = (this.maxFileSize * 1048576); // MB from config properties convert to Bytes
    let fileSize = file.size; // Bytes
    //1048576 byte = 1 MB, 1024 byte = 1 KB

    if (fileSize > maxSize) {
      this.uploadUserErrors.push({ worksheet: file.name, row: null,
        message: 'The selected file size exceeds maximum allowable upload size '  + this.maxFileSize + ' MB',
        systemError: false});

      return true;
    }

    return false;
  }

  checkFileFormat(file) {
    let acceptedFormat = false;

    for (const format of this.acceptedMIMEtypes) {
      if (file.type === format) {
        acceptedFormat = true;
        break;
      }
    }

    if (!acceptedFormat) {
      this.uploadUserErrors.push({ worksheet: file.name, row: null,
          message: 'The file MIME type "' +  file.type + '" with extension "' +
                    file.name.substring(file.name.indexOf('.'), file.name.length + 1)
                    + '" is not in an accepted file format.',
          systemError: false});

      return true;
    }

    return false;
  }

}
