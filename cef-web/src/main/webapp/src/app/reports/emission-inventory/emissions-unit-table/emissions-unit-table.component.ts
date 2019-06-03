import { BaseSortableTable } from '../../../shared/sortable-table/base-sortable-table';
import { EmissionUnit } from '../../model/emission-unit';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-emissions-unit-table',
  templateUrl: './emissions-unit-table.component.html',
  styleUrls: ['./emissions-unit-table.component.scss']
})
export class EmissionsUnitTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: EmissionUnit[];

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
