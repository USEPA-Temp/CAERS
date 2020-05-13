import { Component, OnInit } from '@angular/core';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ActivatedRoute } from '@angular/router';
import { ReportingPeriodService } from 'src/app/core/services/reporting-period.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { BulkEntryReportingPeriod } from 'src/app/shared/models/bulk-entry-reporting-period';
import { EmissionService } from 'src/app/core/services/emission.service';
import { BulkEntryEmissionHolder } from 'src/app/shared/models/bulk-entry-emission-holder';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { ReportStatus } from 'src/app/shared/enums/report-status';

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

  constructor(
    private emissionService: EmissionService,
    private reportingPeriodService: ReportingPeriodService,
    private userContextService: UserContextService,
    private route: ActivatedRoute,
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

}
