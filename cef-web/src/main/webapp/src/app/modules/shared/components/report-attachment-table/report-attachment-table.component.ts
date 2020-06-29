import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FileAttachmentModalComponent } from '../file-attachment-modal/file-attachment-modal.component';
import { FileDownloadService } from 'src/app/core/services/file-download.service';
import { ReportAttachmentService } from 'src/app/core/services/report-attachment.service';
import { ReportService } from 'src/app/core/services/report.service';
import { ReportHistory } from 'src/app/shared/models/report-history';
import { SharedService } from 'src/app/core/services/shared.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-report-attachment-table',
  templateUrl: './report-attachment-table.component.html',
  styleUrls: ['./report-attachment-table.component.scss']
})
export class ReportAttachmentTableComponent extends BaseSortableTable implements OnInit {

    @Input() facilitySite: FacilitySite;
    tableData: ReportHistory[];
    userRole: string;
    reportStatus: string;

    constructor(
                private userContextService: UserContextService,
                private reportService: ReportService,
                private reportAttachmentService: ReportAttachmentService,
                private fileDownloadService: FileDownloadService,
                private sharedService: SharedService,
                private modalService: NgbModal,
                private route: ActivatedRoute) {
        super();
     }

    ngOnInit() {

        this.userContextService.getUser().subscribe( user => {
            this.userRole = user.role;
        });

        this.reportService.retrieveHistory(this.facilitySite.emissionsReport.id, this.facilitySite.id)
        .subscribe(report => {
            this.tableData = report.filter(data =>
                data.userRole !== 'Reviewer' && data.fileName && data.fileName.length > 0 && !data.fileDeleted)
                .sort((a, b) => (a.actionDate < b.actionDate) ? 1 : -1);
        });

        this.reportStatus = this.facilitySite.emissionsReport.status;

    }

    download(data: ReportHistory) {
        this.sharedService.emitReportIdChange(this.facilitySite.emissionsReport.id);
        this.reportAttachmentService.downloadAttachment(data.reportAttachmentId)
        .subscribe(file => {
            this.fileDownloadService.downloadFile(file, data.fileName);
            error => console.error(error);
        });
    }

    openAttachmentModal() {
        const modalRef = this.modalService.open(FileAttachmentModalComponent, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.reportId = this.facilitySite.emissionsReport.id;
        modalRef.componentInstance.title = `Attach Report Document`;
        modalRef.componentInstance.message = `Search for document file to be attached to the ${this.facilitySite.emissionsReport.year} Emissions Report for ${this.facilitySite.name}.`;
        modalRef.result.then(() => {
            this.reportService.retrieveHistory(this.facilitySite.emissionsReport.id, this.facilitySite.id)
            .subscribe(report => {
                this.tableData = report.filter(data => 
                    data.userRole !== 'Reviewer' && data.fileName && data.fileName.length > 0 && !data.fileDeleted)
                    .sort((a, b) => (a.actionDate < b.actionDate) ? 1 : -1);
            });
        }, () => {
            // needed for dismissing without errors
        });
    }

    openDeleteModal(id: number, fileName: string) {
        const modalMessage = `Are you sure you want to delete the attachment ${fileName} from this report?`;
        const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
        modalRef.componentInstance.message = modalMessage;
        modalRef.componentInstance.continue.subscribe(() => {
            this.deleteAttachment(id);
        });
    }

    deleteAttachment(id: number) {
        this.sharedService.emitReportIdChange(this.facilitySite.emissionsReport.id);
        this.reportAttachmentService.deleteAttachment(id).subscribe(() => {
            this.sharedService.updateReportStatusAndEmit(this.route);
            this.reportService.retrieveHistory(this.facilitySite.emissionsReport.id, this.facilitySite.id)
            .subscribe(report => {
                this.tableData = report.filter(data => 
                    data.userRole !== 'Reviewer' && data.fileName && data.fileName.length > 0 && !data.fileDeleted)
                    .sort((a, b) => (a.actionDate < b.actionDate) ? 1 : -1);
            });
        });
    }

}
