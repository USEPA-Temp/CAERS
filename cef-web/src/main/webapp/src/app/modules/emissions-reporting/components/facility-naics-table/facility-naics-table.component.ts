import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FacilityNaicsCode } from 'src/app/shared/models/facility-naics-code';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { FacilityNaicsModalComponent } from '../facility-naics-modal/facility-naics-modal.component';
import { DeleteDialogComponent } from 'src/app/shared/components/delete-dialog/delete-dialog.component';
import { ReportStatus } from 'src/app/shared/enums/report-status';

@Component({
  selector: 'app-facility-naics-table',
  templateUrl: './facility-naics-table.component.html',
  styleUrls: ['./facility-naics-table.component.scss']
})
export class FacilityNaicsTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: FacilityNaicsCode[];
  @Input() readOnlyMode: boolean;
  facilitySiteId: number;
  baseUrl: string;

  constructor(
    private modalService: NgbModal,
    private facilityService: FacilitySiteService,
    private sharedService: SharedService,
    private route: ActivatedRoute) {
    super();
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}`;
    });

    this.route.data
      .subscribe((data: { facilitySite: FacilitySite }) => {
        this.facilitySiteId = data.facilitySite.id;
        this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;
      });

    this.sortTable();
  }

  sortTable() {
    this.tableData.sort((a, b) => (a.description > b.description ? 1 : -1));
    this.tableData.sort((a, b) => a.primaryFlag && !b.primaryFlag ? -1 : !a.primaryFlag && b.primaryFlag ? 1 : 0);
  }

  // delete facility NAICS from the database
  deleteFacilityNaics(facilityNaicsId: number, facilitySiteId: number) {
    this.facilityService.deleteFacilityNaics(facilityNaicsId).subscribe(() => {

      this.sharedService.updateReportStatusAndEmit(this.route);

      // update the UI table with the current list of release point apportionments
      this.facilityService.retrieve(facilitySiteId)
        .subscribe(facilityResponse => {
          this.tableData = facilityResponse.facilityNAICS;
          this.sortTable();
        });
    });
  }

  openDeleteModal(naicsCode: string, facilityNaicsId: number, facilitySiteId: number) {
        const modalMessage = `Are you sure you want to delete NAICS Code ${naicsCode} from this facility?`;
        const modalRef = this.modalService.open(DeleteDialogComponent, { size: 'sm' });
        modalRef.componentInstance.message = modalMessage;
        modalRef.componentInstance.continue.subscribe(() => {
            this.deleteFacilityNaics(facilityNaicsId, facilitySiteId);
        });
    }

  openFacilityNaicsModal() {
    const modalRef = this.modalService.open(FacilityNaicsModalComponent, { size: 'xl', backdrop: 'static', scrollable: true });
    modalRef.componentInstance.facilitySiteId = this.facilitySiteId;
    modalRef.componentInstance.facilityNaics = this.tableData;

    modalRef.result.then(() => {
      this.facilityService.retrieve(this.facilitySiteId)
        .subscribe(facilityResponse => {

          this.sharedService.updateReportStatusAndEmit(this.route);

          this.tableData = facilityResponse.facilityNAICS;
          this.sortTable();
        });

    }, () => {
    //   needed for dismissing without errors
    });
  }

}
