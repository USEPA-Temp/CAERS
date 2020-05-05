import { Component, OnInit } from '@angular/core';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ActivatedRoute } from '@angular/router';
import { ReportingPeriodService } from 'src/app/core/services/reporting-period.service';
import { ToastrService } from 'ngx-toastr';
import { SharedService } from 'src/app/core/services/shared.service';
import { BulkEntryReportingPeriod } from 'src/app/shared/models/bulk-entry-reporting-period';

@Component({
  selector: 'app-data-bulk-entry',
  templateUrl: './data-bulk-entry.component.html',
  styleUrls: ['./data-bulk-entry.component.scss']
})
export class DataBulkEntryComponent implements OnInit {

  facilitySite: FacilitySite;
  reportingPeriods: BulkEntryReportingPeriod[];

  constructor(//private emissionUnitService: EmissionUnitService,
    private reportingPeriodService: ReportingPeriodService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private sharedService: SharedService) { }

  ngOnInit() {

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {

      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);

      this.reportingPeriodService.retrieveForBulkEntry(this.facilitySite.id)
      .subscribe(rp => {
        this.reportingPeriods = rp;
      });
    });
  }

}
