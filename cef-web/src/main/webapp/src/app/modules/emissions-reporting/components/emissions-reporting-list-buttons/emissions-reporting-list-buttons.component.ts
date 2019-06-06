import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-emissions-reporting-list-buttons',
  templateUrl: './emissions-reporting-list-buttons.component.html',
  styleUrls: ['./emissions-reporting-list-buttons.component.scss']
})
export class EmissionsReportingListButtonsComponent implements OnInit {
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
