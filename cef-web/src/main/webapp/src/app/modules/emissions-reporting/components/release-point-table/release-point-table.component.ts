import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-release-point-table',
  templateUrl: './release-point-table.component.html',
  styleUrls: ['./release-point-table.component.scss']
})
export class ReleasePointTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: ReleasePoint[];

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
