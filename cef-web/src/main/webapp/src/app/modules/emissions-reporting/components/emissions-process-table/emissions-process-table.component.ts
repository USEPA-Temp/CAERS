import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { Process } from 'src/app/shared/models/process';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { Component, OnInit, Input } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-emissions-process-table',
  templateUrl: './emissions-process-table.component.html',
  styleUrls: ['./emissions-process-table.component.scss']
})
export class EmissionsProcessTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: Process[];
  @Input() createUrl = '.';
  @Input() parentComponentType: string;
  @Input() readOnlyMode: boolean;
  baseUrl: string;

    constructor(private route: ActivatedRoute,
                private processService: EmissionsProcessService,
                private modalService: NgbModal,
                private sharedService: SharedService) {
        super();
    }

    ngOnInit() {
        this.route.paramMap
            .subscribe(map => {
            this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}`;
        });
    }

    deleteProcess(processId: number, emissionsUnitId: number) {
        this.processService.delete(processId).subscribe(() => {

        // update the table with the list of processes
        this.processService.retrieveForEmissionsUnit(emissionsUnitId)
            .subscribe(processes1 => {
                this.tableData = processes1.sort((a, b) => (a.id > b.id ? 1 : -1));
            });

        // emit the facility data back to the sidebar to reflect the updated
        // list of emission processes
        this.sharedService.updateReportStatusAndEmit(this.route);
        });
    }

    openDeleteModal(processName: string, processId: number, parentId: number) {
        const modalMessage = `Are you sure you want to delete ${processName}? This will also remove any
            Emissions, Control Assignments, and Release Point Assignments associated with this Emissions Process.`;
        const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
        modalRef.componentInstance.message = modalMessage;
        modalRef.componentInstance.continue.subscribe(() => {
            this.deleteProcess(processId, parentId);
        });
    }
}
