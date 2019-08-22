import { Component, OnInit, Input } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { Emission } from 'src/app/shared/models/emission';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EmissionDetailsModalComponent } from 'src/app/modules/emissions-reporting/components/emission-details-modal/emission-details-modal.component';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { Process } from 'src/app/shared/models/process';
import { EmissionService } from 'src/app/core/services/emission.service';
import {ReportingPeriodService} from 'src/app/core/services/reporting-period.service';
import { DeleteDialogComponent } from 'src/app/shared/components/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-emission-table',
  templateUrl: './emission-table.component.html',
  styleUrls: ['./emission-table.component.scss']
})
export class EmissionTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: Emission[];
  @Input() reportingPeriod: ReportingPeriod;
  @Input() process: Process;

    constructor(private modalService: NgbModal,
                private emissionService: EmissionService,
                private reportingPeriodService: ReportingPeriodService) {
        super();
    }

    ngOnInit() {
    }

    openEditEmissionModal(selectedEmission: Emission) {
        const modalRef = this.modalService.open(EmissionDetailsModalComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.emission = selectedEmission;
        modalRef.componentInstance.reportingPeriod = this.reportingPeriod;
        modalRef.componentInstance.process = this.process;
    }

    openCreateEmissionModal() {
        const modalRef = this.modalService.open(EmissionDetailsModalComponent, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.reportingPeriod = this.reportingPeriod;
        modalRef.componentInstance.process = this.process;
        modalRef.componentInstance.createMode = true;

        modalRef.result.then(result => {
        if (result) {
            this.tableData.push(result);
        }
        }, () => {
        // needed for dismissing without errors
        });
    }

    openDeleteModal(emissionName: string, emissionId: number) {
        const modalMessage = `Are you sure you want to delete the pollutant ${emissionName} from this process?`;
        const modalRef = this.modalService.open(DeleteDialogComponent, { size: 'sm' });
        modalRef.componentInstance.message = modalMessage;
        modalRef.componentInstance.continue.subscribe(() => {
            this.deleteEmission(emissionId);
        });
    }

    // delete an emission from the database
    deleteEmission(emissionId: number) {
        this.emissionService.delete(emissionId).subscribe(() => {

        // update the UI table with the current list of emissions
        this.reportingPeriodService.retrieve(this.reportingPeriod.id)
            .subscribe(reportingPeriodResponse => {
                this.tableData = reportingPeriodResponse.emissions;
            });
        });
    }
}
