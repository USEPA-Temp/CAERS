import { Component, OnInit, Input } from '@angular/core';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ActivatedRoute } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';

@Component({
  selector: 'app-control-device-paths-table',
  templateUrl: './control-device-paths-table.component.html',
  styleUrls: ['./control-device-paths-table.component.scss']
})
export class ControlDevicePathsTableComponent implements OnInit {

  @Input() controlPaths: ControlPath[];
  baseUrl : string;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.paramMap
    .subscribe(map => {
      this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}/${BaseReportUrl.CONTROL_PATH}`;
    });
  }

}
