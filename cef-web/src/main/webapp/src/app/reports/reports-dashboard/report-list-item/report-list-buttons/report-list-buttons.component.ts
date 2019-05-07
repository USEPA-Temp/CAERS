import { EmissionsReport } from '../../../../model/emissions-report';
import { UserContextService } from '../../../../services/user-context.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-report-list-buttons',
  templateUrl: './report-list-buttons.component.html',
  styleUrls: ['./report-list-buttons.component.scss']
})
export class ReportListButtonsComponent implements OnInit {
  @Input() report: EmissionsReport;

  constructor(public userContext: UserContextService) { }

  ngOnInit() {
  }

  isOneOf(baseValue: string, testValues: string[]): boolean {
    for (const value of testValues) {
      if (baseValue === value) {
        return true;
      }
    }
    return false;
  }

}
