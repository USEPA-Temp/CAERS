import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { UserFacilityAssociation } from 'src/app/shared/models/user-facility-association';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { UserFacilityAssociationService } from 'src/app/core/services/user-facility-association.service';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

@Component({
  selector: 'app-user-facility-association-table',
  templateUrl: './user-facility-association-table.component.html',
  styleUrls: ['./user-facility-association-table.component.scss']
})
export class UserFacilityAssociationTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: UserFacilityAssociation[];
  @Input() facility: MasterFacilityRecord;

  constructor(private modalService: NgbModal,
              private userFacilityAssociationService: UserFacilityAssociationService) {
    super();
  }

  ngOnInit(): void {
  }

  openDeleteModal(association: UserFacilityAssociation) {
    const modalMessage = `Are you sure you want to remove ${association.fullName}'s access to ${this.facility.name}?`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent);
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
        this.deleteAssociation(association);
    });
  }

  deleteAssociation(association: UserFacilityAssociation) {
    this.userFacilityAssociationService.delete(association.id)
    .subscribe(() => {
      this.tableData.splice(this.tableData.indexOf(association), 1);
    });

  }

}
