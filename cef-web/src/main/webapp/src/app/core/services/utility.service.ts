import { Injectable } from '@angular/core';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor(
    private modalService: NgbModal,
  ) { }

  static isInProgressStatus(status: string): boolean {
    return ReportStatus.IN_PROGRESS === status || ReportStatus.RETURNED === status;
  }

  canDeactivateModal() {
    const config = {backdrop: false};
    const modalMessage = 'There are unsaved edits on the screen. Leaving without saving will discard any changes. Are you sure you want to continue?';
    const modalRef = this.modalService.open(ConfirmationDialogComponent, config);
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.title = 'Unsaved Changes';
    modalRef.componentInstance.confirmButtonText = 'Confirm';
    return modalRef.result;
  }


  static removeSpecialCharacters(input: string): string {
    var regExpr = /[^a-zA-Z0-9-.]/g;
    const output: string = input.replace(regExpr, "");
    return output;
  }

}
