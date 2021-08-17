import { Component, OnInit } from '@angular/core';
import { UserContextService } from 'src/app/core/services/user-context.service';

@Component({
  selector: 'app-emissions-report-container',
  templateUrl: './emissions-report-container.component.html',
  styleUrls: ['./emissions-report-container.component.scss']
})
export class EmissionsReportContainerComponent implements OnInit {
  showProgressBar = false;

  constructor(private userContext: UserContextService) { }

  ngOnInit() {
    if (this.userContext.user.canPrepare()) {
      this.showProgressBar = true;
    }
  }

}
