import { Component, OnInit } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { EisTranactionHistory } from 'src/app/shared/models/eis-tranaction-history';
import { EisDataService } from 'src/app/core/services/eis-data.service';
import { ToastrService } from 'ngx-toastr';
import { EisSubmissionStatus } from 'src/app/shared/models/eis-data';

@Component({
  selector: 'app-eis-transactions',
  templateUrl: './eis-transactions.component.html',
  styleUrls: ['./eis-transactions.component.scss']
})
export class EisTransactionsComponent extends BaseSortableTable implements OnInit {

  tableData: EisTranactionHistory[];

  constructor(private eisDataService: EisDataService,
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
    });
  }

}
