import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { Process } from 'src/app/shared/models/process';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-emissions-process-table',
  templateUrl: './emissions-process-table.component.html',
  styleUrls: ['./emissions-process-table.component.scss']
})
export class EmissionsProcessTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: Process[];
  @Input() createUrl = '.';
  baseUrl: string;

  constructor(private route: ActivatedRoute) {
    super();
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}`;
    });
  }

}
