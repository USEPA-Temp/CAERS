import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CdxFacility} from "src/app/shared/models/cdx-facility";

import bsCustomFileInput from "bs-custom-file-input";
import {EmissionsReportingService} from "src/app/core/services/emissions-reporting.service";
import {BusyModalComponent} from "src/app/shared/components/busy-modal/busy-modal.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'app-report-bulk-upload',
    templateUrl: './report-bulk-upload.component.html',
    styleUrls: ['./report-bulk-upload.component.scss']
})
export class ReportBulkUploadComponent implements OnInit {

    reportingYear: number;
    facility: CdxFacility;
    selectedFile: File;

    uploadErrors: string[];
    uploadFailed: boolean;


    constructor(private emissionsReportingService: EmissionsReportingService,
                private modalService: NgbModal,
                public router: Router,
                private route: ActivatedRoute) {
    }

    ngOnInit() {

        bsCustomFileInput.init("#file-excel-workbook");

        this.route.data
            .subscribe((data: { facility: CdxFacility }) => {
                this.facility = data.facility;
            });

        this.route.paramMap
            .subscribe(params => {

                this.reportingYear = +params.get("year");
            });
    }

    onUploadClick() {

        this.uploadFailed = false;

        const modalWindow = this.modalService.open(BusyModalComponent, {
            backdrop: 'static',
            size: 'lg'
        });

        modalWindow.componentInstance.message = 'Please wait while we parse the workbook and create your report.';

        this.emissionsReportingService.createReportFromUpload(this.facility.programId, this.reportingYear,
            this.facility.epaRegistryId, this.facility.state, this.selectedFile)
            .subscribe(reportResp => {

                // 200 - Success
                modalWindow.dismiss();

                let newReport = reportResp.body;

                return this.router.navigateByUrl(
                    `/facility/${newReport.eisProgramId}/report/${newReport.id}/summary`);

            }, errorResp => {

                // we got an error response

                modalWindow.dismiss();

                this.uploadErrors = errorResp.error.errors;

                this.uploadFailed = true;
            });

    }

    onFileChanged(files: FileList) {

        this.selectedFile = files.item(0);
    }
}
