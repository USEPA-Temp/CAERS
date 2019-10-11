import { Component, OnInit, Input } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { ActivatedRoute } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { ControlPath } from 'src/app/shared/models/control-path';

@Component({
  selector: 'app-control-path-panel',
  templateUrl: './control-path-panel.component.html',
  styleUrls: ['./control-path-panel.component.scss']
})
export class ControlPathPanelComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: ControlPath[];
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