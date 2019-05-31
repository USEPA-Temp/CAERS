import { BaseSortableTable } from '../../../shared/sortable-table/base-sortable-table';
import { EmissionUnit } from '../../model/emission-unit';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-emission-unit-list',
  templateUrl: './emission-unit-list.component.html',
  styleUrls: ['./emission-unit-list.component.scss']
})
export class EmissionUnitListComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: EmissionUnit[];

  constructor() {
    super();
  }

  ngOnInit() {
  }
}
