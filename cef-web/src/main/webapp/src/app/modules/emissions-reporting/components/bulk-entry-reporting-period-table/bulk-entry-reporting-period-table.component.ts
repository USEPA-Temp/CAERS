import { Component, OnInit, Input, OnChanges, Output, EventEmitter } from '@angular/core';
import { BulkEntryReportingPeriod } from 'src/app/shared/models/bulk-entry-reporting-period';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ReportingPeriodService } from 'src/app/core/services/reporting-period.service';
import { BulkEntryEmissionHolder } from 'src/app/shared/models/bulk-entry-emission-holder';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { OperatingStatus } from 'src/app/shared/enums/operating-status';

@Component({
  selector: 'app-bulk-entry-reporting-period-table',
  templateUrl: './bulk-entry-reporting-period-table.component.html',
  styleUrls: ['./bulk-entry-reporting-period-table.component.scss']
})
export class BulkEntryReportingPeriodTableComponent extends BaseSortableTable implements OnInit, OnChanges {
  @Input() tableData: BulkEntryReportingPeriod[];
  @Input() readOnlyMode: boolean;
  @Output() periodsUpdated = new EventEmitter<BulkEntryEmissionHolder[]>();
  baseUrl: string;
  facilitySite: FacilitySite;

  reportingPeriodForm = this.fb.group({});

  operatingStatus = OperatingStatus;

  constructor(
      private reportingPeriodService: ReportingPeriodService,
      private route: ActivatedRoute,
      private fb: FormBuilder,
      private toastr: ToastrService) {
    super();
  }

  ngOnInit() {

    this.tableData.forEach(rp => {

      const disabled = rp.operatingStatusCode.code === OperatingStatus.TEMP_SHUTDOWN
                    || rp.operatingStatusCode.code === OperatingStatus.PERM_SHUTDOWN;
      this.reportingPeriodForm.addControl('' + rp.reportingPeriodId, new FormControl(
        {value: rp.calculationParameterValue, disabled},
        { validators: [
          Validators.required,
          Validators.min(0),
          Validators.pattern('^[0-9]*\\.?[0-9]+$')
        ], updateOn: 'blur'}));
    });

    this.route.paramMap
    .subscribe(map => {
      this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}`;
    });

    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {

      this.facilitySite = data.facilitySite;
    });
  }

  ngOnChanges() {
    this.tableData.sort((a, b) => {

      if (a.unitIdentifier === b.unitIdentifier) {
        return a.emissionsProcessIdentifier > b.emissionsProcessIdentifier ? 1 : -1;
      }
      return a.unitIdentifier > b.unitIdentifier ? 1 : -1;
    });
  }

  onSubmit() {
    if (!this.reportingPeriodForm.valid) {
      this.reportingPeriodForm.markAllAsTouched();
    } else {

      this.tableData.forEach(rp => {
        rp.calculationParameterValue = this.reportingPeriodForm.get('' + rp.reportingPeriodId).value;
      });

      this.reportingPeriodService.bulkUpdate(this.facilitySite.id, this.tableData)
      .subscribe(result => {
        this.periodsUpdated.emit(result);
        this.toastr.success('', 'Throughput values successfully saved and Total Emissions have been recalculated.');
        // reset dirty flags
        this.reportingPeriodForm.markAsPristine();
      });

    }
  }

}
