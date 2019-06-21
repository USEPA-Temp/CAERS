import { Component, OnInit, Input } from '@angular/core';
import { Control } from 'src/app/shared/models/control';
import { ActivatedRoute } from '@angular/router';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';

@Component({
  selector: 'app-inventory-control-table',
  templateUrl: './inventory-control-table.component.html',
  styleUrls: ['./inventory-control-table.component.scss']
})
export class InventoryControlTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: Control[];
  baseUrl: string;

  constructor(private route: ActivatedRoute) {
    super();
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}/${BaseReportUrl.CONTROL_DEVICE}`;
    });
  }

}
