/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { Process } from 'src/app/shared/models/process';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { Component, OnInit, Input } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { SharedService } from 'src/app/core/services/shared.service';
import { faPlus } from '@fortawesome/free-solid-svg-icons';

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
    faPlus = faPlus;

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
            this.route.data
            .subscribe((data: { facilitySite: FacilitySite }) => {
                this.sharedService.emitChange(data.facilitySite);
            });
            this.sharedService.updateReportStatusAndEmit(this.route);
        }, error => {
            if (error.error && error.status === 422) {
                const modalRef = this.modalService.open(ConfirmationDialogComponent);
                modalRef.componentInstance.message = error.error.message;
                modalRef.componentInstance.singleButton = true;
            }
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
