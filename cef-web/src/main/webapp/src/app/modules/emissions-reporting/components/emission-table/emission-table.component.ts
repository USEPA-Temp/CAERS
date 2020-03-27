import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { Emission } from 'src/app/shared/models/emission';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { Process } from 'src/app/shared/models/process';
import { EmissionService } from 'src/app/core/services/emission.service';
import {ReportingPeriodService} from 'src/app/core/services/reporting-period.service';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ActivatedRoute } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-emission-table',
  templateUrl: './emission-table.component.html',
  styleUrls: ['./emission-table.component.scss']
})
export class EmissionTableComponent extends BaseSortableTable implements OnInit, OnChanges {
  @Input() tableData: Emission[];
  @Input() reportingPeriod: ReportingPeriod;
  @Input() process: Process;
  @Input() readOnlyMode: boolean;
  baseUrl: string;

    constructor(private modalService: NgbModal,
                private emissionService: EmissionService,
                private reportingPeriodService: ReportingPeriodService,
                private route: ActivatedRoute,
                private sharedService: SharedService) {
        super();
    }

    ngOnInit() {

        this.tableData.sort((a,b) => (a.pollutant.pollutantName > b.pollutant.pollutantName) ? 1 : -1);

        this.route.paramMap.subscribe(map => {
            this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}/${BaseReportUrl.REPORTING_PERIOD}/${this.reportingPeriod.id}/${BaseReportUrl.EMISSION}`;
        });
    }
    
    ngOnChanges() {
        this.tableData.sort((a,b) => (a.pollutant.pollutantName > b.pollutant.pollutantName) ? 1 : -1);
    }


    openDeleteModal(emissionName: string, emissionId: number) {
        const modalMessage = `Are you sure you want to delete the pollutant ${emissionName} from this process?`;
        const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
        modalRef.componentInstance.message = modalMessage;
        modalRef.componentInstance.continue.subscribe(() => {
            this.deleteEmission(emissionId);
        });
    }

    // delete an emission from the database
    deleteEmission(emissionId: number) {
        this.emissionService.delete(emissionId).subscribe(() => {

        this.sharedService.updateReportStatusAndEmit(this.route);

        // update the UI table with the current list of emissions
        this.reportingPeriodService.retrieve(this.reportingPeriod.id)
            .subscribe(reportingPeriodResponse => {
                this.tableData = reportingPeriodResponse.emissions;
            });
        });
    }
}
