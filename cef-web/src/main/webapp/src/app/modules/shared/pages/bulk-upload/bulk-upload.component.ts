import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import bsCustomFileInput from 'bs-custom-file-input';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-bulk-upload',
  templateUrl: './bulk-upload.component.html',
  styleUrls: ['./bulk-upload.component.scss']
})
export class BulkUploadComponent implements OnInit {
  uploadFiles: File[] = [];
  uploadStatus: string;

  constructor(
      private emissionsReportingService: EmissionsReportingService,
      private route: ActivatedRoute,
      private toastr: ToastrService) { }

  ngOnInit() {
    bsCustomFileInput.init('#file-json-workbook');
  }

  onFileChanged(event) {
    this.uploadStatus = '';
    this.uploadFiles = [];
    for (const f of event.target.files) {
      this.uploadStatus += `'${f.name}' ready to upload<br/>`;
      this.uploadFiles.push(f);
    }
  }

  onUpload() {
    this.uploadStatus += `<br/> ------------------------------------------------------------------------------------- <br/><br/>`;
    this.uploadStatus += `Uploading process beginning`;
    for (const f of this.uploadFiles) {
      const fileReader = new FileReader();
      fileReader.readAsText(f, 'UTF-8');

      fileReader.onload = () => {
        try {
          const jfc = JSON.parse(fileReader.result.toString());
          this.route.paramMap
          .subscribe(map => {
            this.emissionsReportingService.uploadReport(jfc)
            .subscribe(report => {
              console.log(`emissionsReport: ${report.agencyCode}; ${report.eisProgramId}; ${report.facilityId}; ${report.id};
                ${report.status}; ${report.validationStatus}; ${report.year}; `);
              this.uploadStatus += `File for EIS ID ${report.eisProgramId} successfully uploaded.<br/>`;
            }, error => {
              console.log(error);
              this.uploadStatus += `JSON file failed to upload.<br/>`;
            });
          });
        } catch (ex) {
          if (ex instanceof SyntaxError) {
            this.uploadStatus += `'${f.name}' has an invalid file format.  Only json files may be uploaded.<br/>`;
          } else {
            this.uploadStatus += `'${f.name}' failed to upload: ${ex.message}<br/>`;
          }
        }
      };
      fileReader.onerror = (error) => {
          this.uploadStatus += `${f.name} failed to be read: ${error}<br/>`;
      };

    }
    this.uploadStatus += `Uploading process complete`;
    this.toastr.success(`Uploading process complete`);
  }

}
