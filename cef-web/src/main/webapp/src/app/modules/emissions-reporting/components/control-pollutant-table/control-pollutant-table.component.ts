import { Component, OnInit, Input } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { ControlPollutant } from 'src/app/shared/models/control-pollutant';

@Component({
  selector: 'app-control-pollutant-table',
  templateUrl: './control-pollutant-table.component.html',
  styleUrls: ['./control-pollutant-table.component.scss']
})
export class ControlPollutantTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: ControlPollutant[];

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
