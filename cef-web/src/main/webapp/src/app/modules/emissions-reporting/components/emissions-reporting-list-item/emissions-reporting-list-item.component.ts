import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-emissions-reporting-list-item',
  templateUrl: './emissions-reporting-list-item.component.html',
  styleUrls: ['./emissions-reporting-list-item.component.scss']
})
export class EmissionsReportingListItemComponent implements OnInit {
  @Input() report: EmissionsReport;

  constructor() { }

  ngOnInit() {
  }

}
