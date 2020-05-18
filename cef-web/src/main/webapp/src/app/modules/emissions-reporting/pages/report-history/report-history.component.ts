import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import { ReportService } from 'src/app/core/services/report.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { SharedService } from 'src/app/core/services/shared.service';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { ReportAction } from 'src/app/shared/enums/report-action.enum';
import { FileDownloadService } from 'src/app/core/services/file-download.service';
import { ReportAttachmentService } from 'src/app/core/services/report-attachment.service';
import { ReportHistory } from 'src/app/shared/models/report-history';

@Component({
  selector: 'app-report-history',
  templateUrl: './report-history.component.html',
  styleUrls: ['./report-history.component.scss']
})
export class ReportHistoryComponent extends BaseSortableTable implements OnInit {
    facilitySite: FacilitySite;
    tableData: ReportHistory[];
    reportAction: ReportAction;
    emissionsReportId: number;

    constructor(
        private reportService: ReportService,
        private reportAttachmentService: ReportAttachmentService,
        private fileDownloadService: FileDownloadService,
        private route: ActivatedRoute,
        private sharedService: SharedService) {
            super();
        }

    ngOnInit() {
        this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
            this.facilitySite = data.facilitySite;
            this.sharedService.emitChange(data.facilitySite);
            if (this.facilitySite.id) {
                this.emissionsReportId = this.facilitySite.emissionsReport.id;
            }
        });

        this.reportService.retrieveHistory(this.emissionsReportId, this.facilitySite.id)
        .subscribe(report => {
            this.tableData = report;
        });
    }

    enumValue(action: string) {
        return ReportAction[action];
    }

    download(data: ReportHistory) {
        console.log(data)
        this.reportAttachmentService.downloadAttachment(this.facilitySite.id, data.attachmentId)
        .subscribe(file => {
            this.fileDownloadService.downloadFile(file, data.fileName);
            error => console.error(error);
        });
    }

}
