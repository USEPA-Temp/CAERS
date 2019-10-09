import { Component, OnInit, Input } from '@angular/core';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';

@Component({
  selector: 'app-control-path-table',
  templateUrl: './control-path-table.component.html',
  styleUrls: ['./control-path-table.component.scss']
})
export class ControlPathTableComponent extends BaseSortableTable implements OnInit {
    @Input() tableData: ControlAssignment[];

  constructor() {
      super();
  }

  ngOnInit() {
  }

}
