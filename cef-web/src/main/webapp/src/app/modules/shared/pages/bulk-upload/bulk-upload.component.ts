import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import bsCustomFileInput from 'bs-custom-file-input';
import { ToastrService } from 'ngx-toastr';
import {EmissionsReport} from 'src/app/shared/models/emissions-report';

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
    this.uploadStatus += `Uploading process beginning<br/>`;
    for (const f of this.uploadFiles) {
      const fileReader = new FileReader();
      fileReader.readAsText(f, 'UTF-8');

            /*
  .pipe(map((res:Response) => (res.text())))
  .subscribe(data => {console.log(data)});
            */
      fileReader.onload = () => {
        try {
          const jfc = JSON.parse(fileReader.result.toString());
          this.emissionsReportingService.uploadReport(jfc, f.name)
            .subscribe(report => this.onUploadComplete(report),
              error => this.onUploadError(error));
        } catch (ex) {
          if (ex instanceof SyntaxError) {
            this.uploadStatus += `** '${f.name}' has an invalid file format.  Only json files may be uploaded.<br/>`;
          } else {
            this.uploadStatus += `** '${f.name}' failed to upload: ${ex.message}<br/>`;
          }
        }
      };
      fileReader.onerror = (error) => {
          this.uploadStatus += `** '${f.name}' failed to be read: ${error}<br/>`;
      };

    }
    this.uploadStatus += `Uploading process complete<br/>`;
    this.toastr.success(`Uploading process complete`);
  }


  onUploadComplete(report: EmissionsReport) {
        console.log(`emissionsReport: ${report.agencyCode}; ${report.eisProgramId}; ${report.facilityId}; ${report.id};
          ${report.status}; ${report.validationStatus}; ${report.year}; `);
        this.uploadStatus += `** '${decodeURI(report.fileName)}' successfully uploaded.<br/>`;
  }


  onUploadError(resp) {
    for (const err of resp.error.errors) {
        console.log(err.message);
        this.uploadStatus += `** ${err.message}<br/>`;
    }
  }

}
