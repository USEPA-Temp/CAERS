import { Component, OnInit, Input } from '@angular/core';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ActivatedRoute } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ControlPathService } from 'src/app/core/services/control-path.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';

@Component({
  selector: 'app-control-paths-table',
  templateUrl: './control-paths-table.component.html',
  styleUrls: ['./control-paths-table.component.scss']
})
export class ControlPathsTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: ControlPath[];
  baseUrl: string;
  readOnlyMode = true;

  constructor(private route: ActivatedRoute,
              private modalService: NgbModal,
              private controlPathService: ControlPathService,
              private sharedService: SharedService) {
    super();
               }

  ngOnInit() {
    this.route.paramMap
    .subscribe(map => {
      this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}/${BaseReportUrl.CONTROL_PATH}`;
    });

    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;
    });
  }

  openDeleteModal(pathId: string, id: number, facilitySiteId: number) {
    const modalMessage = `Are you sure you want to remove Control Path ${pathId}?`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
      this.deleteControlPath(id, facilitySiteId);
    });
  }

  deleteControlPath(id: number, facilitySiteId: number) {
    this.controlPathService.delete(id).subscribe(() => {
        // update the UI table with the current list of control devices
      this.controlPathService.retrieveForFacilitySite(facilitySiteId)
      .subscribe(controlPathResponse => {
        this.sharedService.updateReportStatusAndEmit(this.route);
        this.tableData = controlPathResponse;
      });
    });
  }

}
