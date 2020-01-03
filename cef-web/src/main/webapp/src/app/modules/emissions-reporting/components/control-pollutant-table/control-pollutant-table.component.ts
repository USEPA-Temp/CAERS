import { Component, OnInit, Input } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { ControlPollutant } from 'src/app/shared/models/control-pollutant';
import { ControlService } from 'src/app/core/services/control.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ControlPollutantModalComponent } from 'src/app/modules/emissions-reporting/components/control-pollutant-modal/control-pollutant-modal.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-control-pollutant-table',
  templateUrl: './control-pollutant-table.component.html',
  styleUrls: ['./control-pollutant-table.component.scss']
})
export class ControlPollutantTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: ControlPollutant[];
  @Input() readOnlyMode: boolean;
  @Input() controlId: number;
  @Input() facilitySiteId: number;


  constructor(private modalService: NgbModal,
              private controlService: ControlService,
              private route: ActivatedRoute,
              private sharedService: SharedService) {
    super();
  }

  ngOnInit() {
  }


  openCreateModal() {
        const modalRef = this.modalService.open(ControlPollutantModalComponent, {size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.controlId = this.controlId;
        modalRef.componentInstance.facilitySiteId = this.facilitySiteId;
        modalRef.componentInstance.controlPollutants = this.tableData;
        modalRef.result.then((result) => {
        this.controlService.retrieve(this.controlId)
        .subscribe(control => {
            if (result !== 'dontUpdate') {
              this.sharedService.updateReportStatusAndEmit(this.route);
            }
            this.tableData = control.pollutants;
          });
        });
  }

  openEditModal(selectedPollutant){
    const modalRef = this.modalService.open(ControlPollutantModalComponent, {size: 'lg', backdrop: 'static'});
    modalRef.componentInstance.facilitySiteId = this.facilitySiteId;
    modalRef.componentInstance.controlId = this.controlId;
    modalRef.componentInstance.selectedControlPollutant = selectedPollutant;
    modalRef.componentInstance.controlPollutants = this.tableData;
    modalRef.componentInstance.edit = true;
  }

  openDeleteModal(selectedPollutant){
    const modalMessage = `Are you sure you want to remove the association of Control Device ${this.controlId}
      with Control Pollutant ${selectedPollutant.pollutant.pollutantName}?`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
      this.deleteControlPollutant(selectedPollutant);
    });
  }

    // delete Control Pollutant from the database
  deleteControlPollutant(selectedPollutant) {
    this.controlService.deletePollutant(selectedPollutant.id).subscribe(() => {

      this.sharedService.updateReportStatusAndEmit(this.route);

      // update the UI table with the current list of control pollutants
      this.controlService.retrieve(this.controlId)
        .subscribe(controlResponse => {
          this.tableData = controlResponse.pollutants;
        });
    });
  }

}
