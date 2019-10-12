import { Component, OnInit, Input } from '@angular/core';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { ActivatedRoute } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';

@Component({
  selector: 'app-control-path-table',
  templateUrl: './control-path-table.component.html',
  styleUrls: ['./control-path-table.component.scss']
})
export class ControlPathTableComponent extends BaseSortableTable implements OnInit {
    @Input() tableData: ControlAssignment[];
    @Input() readOnlyMode: boolean;
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
