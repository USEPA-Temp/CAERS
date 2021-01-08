import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { UserFacilityAssociation } from 'src/app/shared/models/user-facility-association';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { UserFacilityAssociationService } from 'src/app/core/services/user-facility-association.service';

@Component({
  selector: 'app-user-facility-association-table',
  templateUrl: './user-facility-association-table.component.html',
  styleUrls: ['./user-facility-association-table.component.scss']
})
export class UserFacilityAssociationTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: UserFacilityAssociation[];

  constructor(private modalService: NgbModal,
    private userFacilityAssociationService: UserFacilityAssociationService) {
    super();
  }

  ngOnInit(): void {
  }

  openDeleteModal(association: UserFacilityAssociation) {
    const modalMessage = `Are you sure you want to remove ${association.fullName}'s access to this facility?`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent, { size: 'sm' });
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
