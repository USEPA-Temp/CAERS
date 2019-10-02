import { Component, OnInit, Input } from '@angular/core';
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
  @Input() tableData: EmissionsReportItem[];
  baseUrl: string;

  constructor(private route: ActivatedRoute) {
    super();
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}`;
    });

    for (var index in this.tableData) {
    	if (this.tableData[index].type == 'emissionUnit') {
    		this.tableData[index].typeDesc = 'Emissions Unit';
    	} else if (this.tableData[index].type == 'process') {
    		this.tableData[index].typeDesc = 'Emissions Process';
    	} else if (this.tableData[index].type == 'release') {
    		this.tableData[index].typeDesc = 'Release Point';
    	}
    }
  }

}
