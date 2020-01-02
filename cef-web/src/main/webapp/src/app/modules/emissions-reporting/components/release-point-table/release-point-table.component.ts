import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-release-point-table',
  templateUrl: './release-point-table.component.html',
  styleUrls: ['./release-point-table.component.scss']
})
export class ReleasePointTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: ReleasePoint[];
  baseUrl: string;
  facilitySiteId: number;
  
  readOnlyMode = true;

  constructor(private modalService: NgbModal,
              private releasePointService: ReleasePointService,
              private route: ActivatedRoute,
              private sharedService: SharedService) {
    super();
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}/${BaseReportUrl.RELEASE_POINT}`;
    });

    this.route.data
      .subscribe((data: { facilitySite: FacilitySite }) => {
        this.facilitySiteId = (data.facilitySite.id);
        this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;
    });
  }

  openDeleteModal(releasePointIdentifier: string, releasePointId: number) {
    const modalMessage = `Are you sure you want to delete the Release Point ${releasePointIdentifier} from this facility?
      This will also remove any Emission Process apportionments associated with this Release Point.`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
      this.deleteReleasePoint(releasePointId);
    });
  }

  // delete an release point from the database
  deleteReleasePoint(releasePointId: number) {
    this.releasePointService.delete(releasePointId).subscribe(() => {

      this.sharedService.updateReportStatusAndEmit(this.route);

      // update the UI table with the current list of release points
      this.releasePointService.retrieveForFacility(this.facilitySiteId)
        .subscribe(releasePointResponse => {
          this.tableData = releasePointResponse;
      });
    });
  }

}
