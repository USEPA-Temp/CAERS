import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-report-list-item',
  templateUrl: './report-list-item.component.html',
  styleUrls: ['./report-list-item.component.scss']
})
export class ReportListItemComponent implements OnInit {
  @Input() report: EmissionsReport;

  constructor() { }

  ngOnInit() {
  }

}
