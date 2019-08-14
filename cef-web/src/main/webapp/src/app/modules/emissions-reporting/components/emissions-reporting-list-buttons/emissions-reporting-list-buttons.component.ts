import { Component, OnInit, Input } from '@angular/core';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BusyModalComponent} from 'src/app/shared/components/busy-modal/busy-modal.component';
import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { Router } from '@angular/router';

@Component({
  selector: 'app-emissions-reporting-list-buttons',
  templateUrl: './emissions-reporting-list-buttons.component.html',
  styleUrls: ['./emissions-reporting-list-buttons.component.scss']
})
export class EmissionsReportingListButtonsComponent implements OnInit {
  @Input() report: EmissionsReport;
  @Input() facility: CdxFacility;

  constructor(public userContext: UserContextService,
              private reportService: EmissionsReportingService,
              public router: Router,
              private modalService: NgbModal) { }

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


  /**
   * Create a new report.  Either a copy of the previous report (if one exists) or a new report
   */
  createNewReport() {

    const modalWindow = this.modalService.open(BusyModalComponent, { size: 'lg' });
    modalWindow.componentInstance.message = `Please wait while we generate your new report.`;

    const currentYear: number = new Date().getFullYear();
    this.reportService.createReport(this.facility.programId, currentYear).subscribe(newReport => this.reportCompleted(newReport));
  }


  /**
   * Callback that is triggered after the new report is created
   */
  reportCompleted(newReport: EmissionsReport) {
      this.modalService.dismissAll();
      this.router.navigateByUrl(`/facility/${newReport.eisProgramId}/report/${newReport.id}/summary`);
  }

}
