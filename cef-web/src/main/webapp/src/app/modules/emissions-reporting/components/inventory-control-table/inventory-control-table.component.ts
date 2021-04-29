import { Component, OnInit, Input } from '@angular/core';
import { Control } from 'src/app/shared/models/control';
import { ActivatedRoute } from '@angular/router';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ControlService } from 'src/app/core/services/control.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { faPlus } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-inventory-control-table',
  templateUrl: './inventory-control-table.component.html',
  styleUrls: ['./inventory-control-table.component.scss']
})
export class InventoryControlTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: Control[];
  baseUrl: string;
  faPlus = faPlus;

  readOnlyMode = true;

  constructor(private modalService: NgbModal,
              private route: ActivatedRoute,
              private controlService: ControlService,
              private userContextService: UserContextService,
              private sharedService: SharedService) {
    super();
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}/${BaseReportUrl.CONTROL_DEVICE}`;
    });

    this.route.data
      .subscribe((data: { facilitySite: FacilitySite }) => {
        this.userContextService.getUser().subscribe( user => {
          if (user.role !== 'Reviewer' && ReportStatus.IN_PROGRESS === data.facilitySite.emissionsReport.status) {
            this.readOnlyMode = false;
          }
        });
      });
  }

  openDeleteModal(controlName: string, controlId: number, facilitySiteId: number) {
    const modalMessage = `Are you sure you want to remove Control Device ${controlName}? This will delete
          the control device along with any associated control assignments and control paths.`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
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
          this.sharedService.updateReportStatusAndEmit(this.route);
          this.tableData = controlResponse;
        });

    }, error => {
      if (error.error && error.status === 422) {
        const modalRef = this.modalService.open(ConfirmationDialogComponent);
        modalRef.componentInstance.message = error.error.message;
        modalRef.componentInstance.singleButton = true;
      }
    });
  }

}
