import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';

@Component({
  selector: 'app-bulk-upload',
  templateUrl: './bulk-upload.component.html',
  styleUrls: ['./bulk-upload.component.scss']
})
export class BulkUploadComponent implements OnInit {
    selectedFile: File;
    jsonFileContents: string;
    reportUpload: string;

  constructor(
      private emissionsReportingService: EmissionsReportingService,
      private route: ActivatedRoute) { }

  ngOnInit() {
  }

  onFileChanged(event) {
    this.selectedFile = event.target.files[0];
    const fileReader = new FileReader();
    fileReader.readAsText(this.selectedFile, 'UTF-8');
    fileReader.onload = () => {
    this.jsonFileContents = JSON.parse(fileReader.result.toString());
    };
    fileReader.onerror = (error) => {
        console.log(error);
    };
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
      });
    });
  }

}
