import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ActivatedRoute } from '@angular/router';
import { ReportingPeriodService } from 'src/app/core/services/reporting-period.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { BulkEntryReportingPeriod } from 'src/app/shared/models/bulk-entry-reporting-period';
import { EmissionService } from 'src/app/core/services/emission.service';
import { BulkEntryEmissionHolder } from 'src/app/shared/models/bulk-entry-emission-holder';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { BulkEntryEmissionsTableComponent } from 'src/app/modules/emissions-reporting/components/bulk-entry-emissions-table/bulk-entry-emissions-table.component';
import { BulkEntryReportingPeriodTableComponent } from 'src/app/modules/emissions-reporting/components/bulk-entry-reporting-period-table/bulk-entry-reporting-period-table.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-data-bulk-entry',
  templateUrl: './data-bulk-entry.component.html',
  styleUrls: ['./data-bulk-entry.component.scss']
})
export class DataBulkEntryComponent implements OnInit {

  facilitySite: FacilitySite;
  reportingPeriods: BulkEntryReportingPeriod[];
  emissions: BulkEntryEmissionHolder[];

  readOnlyMode = true;

  @ViewChild(BulkEntryReportingPeriodTableComponent)
  periodComponent: BulkEntryReportingPeriodTableComponent;

  @ViewChild(BulkEntryEmissionsTableComponent)
  emissionComponent: BulkEntryEmissionsTableComponent;

  constructor(
    private emissionService: EmissionService,
    private reportingPeriodService: ReportingPeriodService,
    private userContextService: UserContextService,
    private route: ActivatedRoute,
    private modalService: NgbModal,
    private sharedService: SharedService) { }

  ngOnInit() {

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {

      this.facilitySite = data.facilitySite;

      // TODO: this should be turned into a reusable call to reduce code duplication
      this.userContextService.getUser().subscribe( user => {
        if (user.role !== 'Reviewer' && ReportStatus.IN_PROGRESS === data.facilitySite.emissionsReport.status) {
          this.readOnlyMode = false;
        }
      });

      this.sharedService.emitChange(data.facilitySite);

      this.reportingPeriodService.retrieveForBulkEntry(this.facilitySite.id)
      .subscribe(rp => {
        this.reportingPeriods = rp;
      });

      this.emissionService.retrieveForBulkEntry(this.facilitySite.id)
      .subscribe(rp => {
        this.emissions = rp;
      });
    });
  }

  onEmissionsUpdated(updatedEmissions: BulkEntryEmissionHolder[]) {

    this.emissions = updatedEmissions;
  }

  onPeriodsUpdated(updatedPeriods: BulkEntryEmissionHolder[]) {

    this.emissions = updatedPeriods;
  }

  canDeactivate(): Promise<boolean> | boolean {
    let message;
    // Allow synchronous navigation (`true`) if both forms are clean
    if (this.readOnlyMode || (!this.periodComponent.reportingPeriodForm.dirty && !this.emissionComponent.emissionForm.dirty)) {
      return true;
    }

    if (this.periodComponent.reportingPeriodForm.dirty && this.emissionComponent.emissionForm.dirty) {
      message = 'You have unsaved changes which will be lost if you navigate away. Are you sure you wish to discard these changes?';
    } else if (this.periodComponent.reportingPeriodForm.dirty) {
      message = 'You have unsaved Process Information changes which will be lost if you navigate away. Are you sure you wish to discard these changes?';
    } else {
      message = 'You have unsaved Emission Information changes which will be lost if you navigate away. Are you sure you wish to discard these changes?';
    }

    // Otherwise ask the user with the dialog service and return its
    // promise which resolves to true or false when the user decides
    const modalMessage = message;
    const modalRef = this.modalService.open(ConfirmationDialogComponent);
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.title = 'Unsaved Changes';
    modalRef.componentInstance.confirmButtonText = 'Discard';
    return modalRef.result;
  }

  @HostListener('window:beforeunload', ['$event'])
  beforeunloadHandler(event) {
    if (!this.readOnlyMode && (this.periodComponent.reportingPeriodForm.dirty || this.emissionComponent.emissionForm.dirty)) {
      event.preventDefault();
      event.returnValue = '';
    }
    return true;
  }

}
