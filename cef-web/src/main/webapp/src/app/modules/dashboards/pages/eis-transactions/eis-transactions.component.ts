/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
import { Component, OnInit } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { EisTranactionHistory } from 'src/app/shared/models/eis-tranaction-history';
import { EisDataService } from 'src/app/core/services/eis-data.service';
import { ToastrService } from 'ngx-toastr';
import { EisSubmissionStatus } from 'src/app/shared/models/eis-data';
import { FileDownloadService } from 'src/app/core/services/file-download.service';
import { EisTransactionAttachment } from 'src/app/shared/models/eis-transaction-attachment';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EisTransactionAttachmentModalComponent } from 'src/app/modules/shared/components/eis-transaction-attachment-modal/eis-transaction-attachment-modal.component';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-eis-transactions',
  templateUrl: './eis-transactions.component.html',
  styleUrls: ['./eis-transactions.component.scss']
})
export class EisTransactionsComponent extends BaseSortableTable implements OnInit {

  tableData: EisTranactionHistory[];

  page = 1;
  pageSize = 25;

  constructor(private eisDataService: EisDataService,
              private fileDownloadService: FileDownloadService,
              private modalService: NgbModal,
              private toastr: ToastrService) {

    super();
  }

  ngOnInit() {

    this.eisDataService.retrieveTransactionHistory()
    .subscribe(result => {
      this.tableData = result.map(record => {

        if (record.eisSubmissionStatus) {

          record.eisSubmissionStatus = EisSubmissionStatus[record.eisSubmissionStatus];

        }

        return record;
      });

      this.onSort({column: 'createdDate', direction: 'desc'});
    });

    this.controller.paginate = true;
  }

  private refreshHistory() {

    this.eisDataService.retrieveTransactionHistory()
    .subscribe(result => {
      this.tableData = result.map(record => {

        if (record.eisSubmissionStatus) {

          record.eisSubmissionStatus = EisSubmissionStatus[record.eisSubmissionStatus];

        }

        return record;
      });

      this.resortTable();
    });
  }

  download(data: EisTransactionAttachment) {
    this.eisDataService.downloadAttachment(data.id)
    .subscribe(file => {
        this.fileDownloadService.downloadFile(file, data.fileName);
        error => {
          console.error(error);
          this.toastr.error('', 'An error occurred while trying to download this report.');
        };
    });
  }

  openAttachmentModal(id: number) {
    const modalRef = this.modalService.open(EisTransactionAttachmentModalComponent, {size: 'lg', backdrop: 'static'});
    modalRef.componentInstance.transactionHistoryId = id;
    modalRef.componentInstance.title = `Attach Feedback Report`;
    modalRef.componentInstance.message = `Search for feedback report file to be attached to this transaction.`;
    modalRef.result.then(() => {
      this.refreshHistory();
    }, () => {
        // needed for dismissing without errors
    });
  }

  openDeleteModal(id: number, fileName: string) {
    const modalMessage = `Are you sure you want to delete the attachment ${fileName} from this record?`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
      this.deleteAttachment(id);
    });
  }

  deleteAttachment(id: number) {
    this.eisDataService.deleteAttachment(id).subscribe(() => {

      this.refreshHistory();
    });
  }

}
