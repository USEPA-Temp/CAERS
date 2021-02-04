import {Component, OnInit, TemplateRef, ViewChild, ElementRef} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CdxFacility} from 'src/app/shared/models/cdx-facility';

import bsCustomFileInput from 'bs-custom-file-input';

import {EmissionsReportingService} from 'src/app/core/services/emissions-reporting.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap/modal/modal-ref';
import {HttpEvent, HttpEventType} from '@angular/common/http';
import {EMPTY} from 'rxjs';
import {UserService} from 'src/app/core/services/user.service';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

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
    selector: 'app-report-bulk-upload',
    templateUrl: './report-bulk-upload.component.html',
    styleUrls: ['./report-bulk-upload.component.scss']
})
export class ReportBulkUploadComponent implements OnInit {

    reportingYear: number;
    facility: MasterFacilityRecord;
    selectedFile: File = null;

    bsflags: any;

    @ViewChild('workbookFile', {static: true})
    workbookFile: ElementRef;

    uploadFile: string;
    uploadUserErrors: WorksheetError[];
    uploadSystemErrors: WorksheetError[];
    uploadFailed: boolean;

    @ViewChild('PleaseWaitModal', {static: true})
    pleaseWaitTemplate: TemplateRef<any>;

    pleaseWait: PleaseWaitConfig;

    constructor(private emissionsReportingService: EmissionsReportingService,
                private userService: UserService,
                private modalService: NgbModal,
                public router: Router,
                private route: ActivatedRoute) {

        this.bsflags = {
            showSystemErrors: false,
            showUserErrors: true
        };
    }

    ngOnInit() {

        bsCustomFileInput.init('#file-excel-workbook');

        this.route.data
            .subscribe((data: { facility: MasterFacilityRecord }) => {
                this.facility = data.facility;
            });

        this.route.paramMap
            .subscribe(params => {

                this.reportingYear = +params.get('year');
            });
    }

    onUploadClick() {

        if (this.selectedFile) {

            this.uploadFailed = false;
            this.uploadFile = this.selectedFile.name;

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

            this.emissionsReportingService.createReportFromUpload(this.facility, this.reportingYear, this.selectedFile)
                .subscribe(respEvent => this.onUploadEvent(respEvent),
                    errorResp => this.onUploadError(errorResp));
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

                    this.pleaseWait.message = 'The server is parsing, validating and saving file...';
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

                const newReport = event.body;

                return this.router.navigateByUrl(
                    `/facility/${newReport.eisProgramId}/report/${newReport.id}/summary`);

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
        this.workbookFile.nativeElement.value = '';
        this.selectedFile = null;
    }

    onUploadComplete() {

        this.pleaseWait.modal.dismiss();

        clearInterval(this.pleaseWait.serverTicker);
        this.pleaseWait.serverTicker = null;

        clearInterval(this.pleaseWait.keepAliveTicker);
        this.pleaseWait.keepAliveTicker = null;
    }

    onFileChanged(files: FileList) {

        this.selectedFile = files.length ? files.item(0) : null;
    }
}
