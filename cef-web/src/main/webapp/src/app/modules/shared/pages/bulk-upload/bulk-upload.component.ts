import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { ToastrService } from 'ngx-toastr';
import bsCustomFileInput from "bs-custom-file-input";

@Component({
  selector: 'app-bulk-upload',
  templateUrl: './bulk-upload.component.html',
  styleUrls: ['./bulk-upload.component.scss']
})
export class BulkUploadComponent implements OnInit {
    selectedFile:File = null;
    jsonFileContents: string;
    reportUpload: string;

  constructor(
      private emissionsReportingService: EmissionsReportingService,
      private route: ActivatedRoute,
      private toastr: ToastrService) { }

  ngOnInit() {
    bsCustomFileInput.init("#file-json-workbook");
  }

  onFileChanged(event) {
    this.selectedFile = event.target.files[0];
    const fileReader = new FileReader();
    
    //checks to make sure file is selected so that canceling doesnt cause error
    if (event.target.files[0]) {
      fileReader.readAsText(this.selectedFile, 'UTF-8');

      fileReader.onload = () => {
        try {
          this.jsonFileContents = JSON.parse(fileReader.result.toString());
        } catch {
          this.toastr.error('', 'Invalid file format, only json files may be uploaded.', {positionClass: 'toast-top-right'});
          this.selectedFile = null;
        }
      };
      fileReader.onerror = (error) => {
          console.log(error);
      };
    }
  }

  onUpload() {
    this.route.paramMap
    .subscribe(map => {
        this.emissionsReportingService.uploadReport(this.jsonFileContents)
        .subscribe(report => {
          console.log(`emissionsReport: ${report.agencyCode}; ${report.eisProgramId}; ${report.facilityId}; ${report.id};
              ${report.status}; ${report.validationStatus}; ${report.year}; `);
          this.reportUpload = `emissionsReport: ${report.agencyCode}; ${report.eisProgramId}; ${report.facilityId}; ${report.id};
              ${report.status}; ${report.validationStatus}; ${report.year}; `;
          this.toastr.success('', 'File successfully uploaded.', {positionClass: 'toast-top-right'})
        }, error => {
          this.toastr.error('', 'The json file failed to upload, please check your formatting and try again.', {positionClass: 'toast-top-right'});
        });
    });
  }

}
