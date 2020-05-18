import { Component, OnInit, Input, ViewChild, ElementRef, TemplateRef } from '@angular/core';
import { NgbActiveModal, NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Validators, FormBuilder } from '@angular/forms';
import bsCustomFileInput from 'bs-custom-file-input';
import { HttpEvent, HttpEventType } from '@angular/common/http';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { EMPTY } from 'rxjs';
import { UserService } from 'src/app/core/services/user.service';
import { ReportAttachmentService } from 'src/app/core/services/report-attachment.service';
import { ReportAttachment } from 'src/app/shared/models/report-attachment';

interface PleaseWaitConfig {
    modal: NgbModalRef;
    message: string;
    progress: number;
    serverTicker: number;
    keepAliveTicker: number;
}

interface AttachmentError {
    attachmentString: string;
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
  @Input() confirmButtonText = 'Upload';
  @Input() facilitySite: FacilitySite;
  attachment: ReportAttachment;
  acceptedFileFormats: string[];
  selectedFile: File = null;
  bsflags: any;

  @ViewChild('fileAttachment', {static: true})
  fileAttachment: ElementRef;

  uploadFile: string;
  uploadFailed: boolean;
  uploadUserErrors: AttachmentError[];
  uploadSystemErrors: AttachmentError[];

  @ViewChild('PleaseWaitModal', {static: true})
  pleaseWaitTemplate: TemplateRef<any>;

  pleaseWait: PleaseWaitConfig;

  attachmentForm = this.fb.group({
    comments: ['', [Validators.maxLength(200)]]
  });

  constructor(public activeModal: NgbActiveModal,
              private fb: FormBuilder,
              private reportAttachmentService: ReportAttachmentService,
              private userService: UserService,
              private modalService: NgbModal) {

    this.bsflags = {
      showSystemErrors: false,
      showUserErrors: true
    };
  }

  ngOnInit() {
    bsCustomFileInput.init('#file-attachment');

    this.acceptedFileFormats = [
      '.xlsx',
      '.csv',
      '.docx',
      '.txt',
      '.pdf'
    ];
  }

  onClose() {
    this.activeModal.dismiss();
  }

  isValid() {
    return this.attachmentForm.valid;
  }

  onSubmit() {

    if (!this.isValid()) {
      this.attachmentForm.markAllAsTouched();
      this.attachmentForm.controls.control.markAsDirty();
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
      reportAttachment.reportId = this.facilitySite.emissionsReport.id;
      Object.assign(reportAttachment, this.attachmentForm.value);

      this.reportAttachmentService.uploadAttachment(
        this.facilitySite.id, reportAttachment, this.selectedFile)
        .subscribe(respEvent =>
          this. onUploadEvent(respEvent),
          errorResp => this.onUploadError(errorResp)
        );
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

                this.activeModal.close();

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
    }

    onUploadComplete() {

        this.pleaseWait.modal.dismiss();

        clearInterval(this.pleaseWait.serverTicker);
        this.pleaseWait.serverTicker = null;

        clearInterval(this.pleaseWait.keepAliveTicker);
        this.pleaseWait.keepAliveTicker = null;
    }

  onFileChanged(file: FileList) {
    this.selectedFile = file.length ? file.item(0) : null;
    this.uploadUserErrors = [];

    if (file.item(0)) {
      this.selectedFile = file.item(0);
      const fileReader = new FileReader();

      fileReader.readAsText(this.selectedFile, 'UTF-8');

      fileReader.onload = () => {
          const fileName = file.item(0).name.toLowerCase();
          let fileFormat = this.checkFileFormat(fileName);
          let fileSize = this.checkFileSize(this.selectedFile);

          if (!fileFormat || fileSize) {

            this.bsflags.showUserErrors = true;
            this.uploadFailed = true;

            this.selectedFile = null;
          }
        };

      fileReader.onerror = (error) => {
          console.log(error);
      };
    }
  }

  checkFileFormat(fileName: string) {
    let acceptedFormat = false;

    for (const format of this.acceptedFileFormats) {
      if (fileName.endsWith(format)) {
        acceptedFormat = true;
        break;
      }
    }

    if (!acceptedFormat) {
      this.uploadUserErrors.push({ attachmentString: fileName,
          message: 'The file extension "' + fileName.substring(fileName.indexOf('.'), fileName.length + 1)
                    + '" is not in an accepted file format.',
          systemError: false});
    }

    return acceptedFormat;
  }

  checkFileSize(file) {
    const maxFileSize = 1024 * 1024 * 10; // 10 MB
    let fileSize = Math.round(file.size / 1048576); //MB
    //1048576 byte = 1 MB, 1024 byte = 1 KB

    if (fileSize > maxFileSize) {
      this.uploadUserErrors.push({ attachmentString: file.name,
        message: 'The selected file size, ' + fileSize + ' MB, exceeds maximum allowable upload size '  + maxFileSize + ' MB',
        systemError: false});

      return true;
    }

    return false;
  }

}
