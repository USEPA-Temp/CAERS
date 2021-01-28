import { Component, OnInit } from '@angular/core';
import { UserFacilityAssociationService } from 'src/app/core/services/user-facility-association.service';
import { UserFacilityAssociation } from 'src/app/shared/models/user-facility-association';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { CommentModalComponent } from 'src/app/modules/shared/components/comment-modal/comment-modal.component';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-pending-user-facility-associations',
  templateUrl: './pending-user-facility-associations.component.html',
  styleUrls: ['./pending-user-facility-associations.component.scss']
})
export class PendingUserFacilityAssociationsComponent extends BaseSortableTable implements OnInit {
  tableData: UserFacilityAssociation[] = [];

  constructor(private modalService: NgbModal,
              private userFacilityAssociationService: UserFacilityAssociationService,
              private toastr: ToastrService) {
    super();
  }

  ngOnInit(): void {
    this.userFacilityAssociationService.getPendingAssociationDetails()
    .subscribe(result => {
      this.tableData = result;
    });
  }

  selectedAssociations() {
    return this.tableData?.filter(i => i.checked);
  }

  openApproveModal() {
    const modalMessage = `Are you sure you want to approve these users' access to these facilities?`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent);
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
      this.approveAssociations();
    });
  }

  openRejectModal() {
    const modalMessage = `Are you sure you want to reject these users' access to these facilities?`;
    const modalRef = this.modalService.open(CommentModalComponent);
    modalRef.componentInstance.title = 'Reject Requests';
    modalRef.componentInstance.message = modalMessage;
    modalRef.result.then((resp) => {
      this.rejectAssociations(resp);
    }, () => {
      // needed for dismissing without errors
    });
  }

  approveAssociations() {
    const selectedAssociations = this.selectedAssociations();
    this.userFacilityAssociationService.approveAssociations(selectedAssociations)
    .subscribe(() => {
      selectedAssociations.forEach(ufa => {
        this.tableData.splice(this.tableData.indexOf(ufa), 1);
      });
      this.toastr.success('', 'Authorization Requests were successfully approved.');
    });
  }

  rejectAssociations(comments: string) {
    const selectedAssociations = this.selectedAssociations();
    this.userFacilityAssociationService.rejectAssociations(selectedAssociations, comments)
    .subscribe(() => {
      selectedAssociations.forEach(ufa => {
        this.tableData.splice(this.tableData.indexOf(ufa), 1);
      });
      this.toastr.success('', 'Authorization Requests were successfully rejected.');
    });
  }

}
