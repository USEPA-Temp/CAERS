import { Component, OnInit, Input } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { ActivatedRoute } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';

@Component({
  selector: 'app-control-devices-table',
  templateUrl: './control-devices-table.component.html',
  styleUrls: ['./control-devices-table.component.scss']
})
export class ControlDevicesTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: ControlAssignment[];
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
