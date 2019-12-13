import { Component, OnInit, Input } from '@angular/core';
import { ReleasePointApportionment } from 'src/app/shared/models/release-point-apportionment';
import { ActivatedRoute } from '@angular/router';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DeleteDialogComponent } from 'src/app/shared/components/delete-dialog/delete-dialog.component';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReleasePointApportionmentModalComponent } from 'src/app/modules/emissions-reporting/components/release-point-apportionment-modal/release-point-apportionment-modal.component';
import { Process } from 'src/app/shared/models/process';

@Component({
  selector: 'app-release-point-appt-table',
  templateUrl: './release-point-appt-table.component.html',
  styleUrls: ['./release-point-appt-table.component.scss']
})
export class ReleasePointApptTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: ReleasePointApportionment[];
  @Input() process: Process;
  @Input() readOnlyMode: boolean;
  @Input() facilitySite: FacilitySite;
  baseUrl: string;

  constructor(
    private modalService: NgbModal,
    private processService: EmissionsProcessService,
    private releasePointService: ReleasePointService,
    private route: ActivatedRoute) {
    super();
  }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}`;
    });
    this.tableData.sort((a, b) => (a.releasePointIdentifier > b.releasePointIdentifier? 1 : -1));
  }

  // delete release point apportionment from the database
  deleteReleasePointApportionment(releasePtApptId: number, emissionProcessId: number) {
    this.releasePointService.deleteAppt(releasePtApptId).subscribe(() => {

      // update the UI table with the current list of release point apportionments
      this.processService.retrieve(emissionProcessId)
        .subscribe(processResponse => {
          this.tableData = processResponse.releasePointAppts;
        });
    });
  }

  openDeleteModal(releasePtApptIdentifier: string, releasePtApptId: number) {
    const modalMessage = `Are you sure you want to remove ${releasePtApptIdentifier} Release Point from the association of
      with ${this.process.emissionsProcessIdentifier} Emissions Process?  The apportionment of emissions will need to be updated to
      total 100% for the remaining release points afterwards.`;
    const modalRef = this.modalService.open(DeleteDialogComponent, { size: 'sm' });
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
      this.deleteReleasePointApportionment(releasePtApptId, this.process.id);
    });
  }

  openReleasePointAptModal(){
        const modalRef = this.modalService.open(ReleasePointApportionmentModalComponent, { size: 'xl', backdrop: 'static', scrollable: true });
        modalRef.componentInstance.process = this.process;
        modalRef.componentInstance.releasePointApportionments = this.tableData;
        modalRef.componentInstance.facilitySiteId = this.facilitySite.id;
  
        modalRef.result.then(() => {
          this.processService.retrieve(this.process.id)
          .subscribe(processResponse => {
            this.tableData = processResponse.releasePointAppts;
          })
        })
  }
}