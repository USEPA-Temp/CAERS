import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CdxFacility} from "src/app/shared/models/cdx-facility";

import bsCustomFileInput from "bs-custom-file-input";
import {EmissionsReportingService} from "../../../../core/services/emissions-reporting.service";

@Component({
    selector: 'app-report-bulk-upload',
    templateUrl: './report-bulk-upload.component.html',
    styleUrls: ['./report-bulk-upload.component.scss']
})
export class ReportBulkUploadComponent implements OnInit {

    reportingYear: number;
    facility: CdxFacility;
    selectedFile: File;

    constructor(private emissionsReportingService: EmissionsReportingService,
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

        this.emissionsReportingService.createReportFromUpload(this.facility.programId, this.reportingYear,
            this.facility.epaRegistryId, this.facility.state, this.selectedFile)
            .subscribe(report => {

                console.log(report);
            });

    }

    onFileChanged(files: FileList) {

        this.selectedFile = files.item(0);
    }
}
