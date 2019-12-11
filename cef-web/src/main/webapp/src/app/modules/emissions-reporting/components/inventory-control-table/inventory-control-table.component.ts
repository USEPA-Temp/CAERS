import { Component, OnInit, Input } from '@angular/core';
import { Control } from 'src/app/shared/models/control';
import { ActivatedRoute } from '@angular/router';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DeleteDialogComponent } from 'src/app/shared/components/delete-dialog/delete-dialog.component';
import { ControlService } from 'src/app/core/services/control.service';

@Component({
  selector: 'app-inventory-control-table',
  templateUrl: './inventory-control-table.component.html',
  styleUrls: ['./inventory-control-table.component.scss']
})
export class InventoryControlTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: Control[];
  baseUrl: string;

  readOnlyMode = true;

  constructor(private modalService: NgbModal,
              private route: ActivatedRoute,
              private controlService: ControlService) {
    super();
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}/${BaseReportUrl.CONTROL_DEVICE}`;
    });

    this.route.data
      .subscribe((data: { facilitySite: FacilitySite }) => {
        this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;

      });
  }

  openDeleteModal(controlName: string, controlId: number, facilitySiteId: number) {
    const modalMessage = `Are you sure you want to remove Control Device ${controlName}? This will delete
          the control device along with any associated control assignments and control paths.`;
    const modalRef = this.modalService.open(DeleteDialogComponent, { size: 'sm' });
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
      this.deleteControl(controlId, facilitySiteId);
    });
  }

  // delete a control device from the database
  deleteControl(controlId: number, facilitySiteId: number) {
    this.controlService.delete(controlId).subscribe(() => {

      // update the UI table with the current list of control devices
      this.controlService.retrieveForFacilitySite(facilitySiteId)
        .subscribe(controlResponse => {
          this.tableData = controlResponse;
        });

    });
  }

}
