import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CdxFacility} from "src/app/shared/models/cdx-facility";

import bsCustomFileInput from "bs-custom-file-input";

@Component({
    selector: 'app-report-bulk-upload',
    templateUrl: './report-bulk-upload.component.html',
    styleUrls: ['./report-bulk-upload.component.scss']
})
export class ReportBulkUploadComponent implements OnInit {

    reportingYear: number;
    facility: CdxFacility;

    constructor(private route: ActivatedRoute) {
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

}
