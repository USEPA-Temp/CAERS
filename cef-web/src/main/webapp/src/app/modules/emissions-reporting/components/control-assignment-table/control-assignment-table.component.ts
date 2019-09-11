import { Component, OnInit, Input } from '@angular/core';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { ActivatedRoute } from '@angular/router';
import { EmissionsReportItem } from 'src/app/shared/models/emissions-report-item';

@Component({
  selector: 'app-control-assignment-table',
  templateUrl: './control-assignment-table.component.html',
  styleUrls: ['./control-assignment-table.component.scss']
})
export class ControlAssignmentTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: ControlAssignment[];
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
