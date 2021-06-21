import { Component, OnInit, Input, ViewChild, HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { EditControlPathInfoPanelComponent } from '../../components/edit-control-path-info-panel/edit-control-path-info-panel.component';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ControlPathService } from 'src/app/core/services/control-path.service';
import { SharedService } from 'src/app/core/services/shared.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-create-control-path',
  templateUrl: './create-control-path.component.html',
  styleUrls: ['./create-control-path.component.scss']
})
export class CreateControlPathComponent implements OnInit {
  @Input() facilitySite: FacilitySite;
  controlPathUrl: string;
  editInfo = true;

  @ViewChild(EditControlPathInfoPanelComponent, { static: true })
  private controlPathComponent: EditControlPathInfoPanelComponent;

  constructor(private route: ActivatedRoute,
              private controlPathService: ControlPathService,
              private sharedService: SharedService,
              private modalService: NgbModal,
              private router: Router) { }

  ngOnInit() {
    this.route.data
    .subscribe(data => {
      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);
    });

    this.route.paramMap
    .subscribe(params => {
      this.controlPathUrl = `/facility/${params.get('facilityId')}/report/${params.get('reportId')}/${BaseReportUrl.CONTROL_PATH}`;
    });
  }

  isValid() {
    return this.controlPathComponent.controlPathForm.valid;
  }

  onSubmit() {

    if (!this.isValid()) {
      this.controlPathComponent.controlPathForm.markAllAsTouched();
    } else {
      const saveControlPath = new ControlPath();

      Object.assign(saveControlPath, this.controlPathComponent.controlPathForm.value);
      saveControlPath.facilitySiteId = this.facilitySite.id;

      this.controlPathService.create(saveControlPath)
        .subscribe(() => {
          this.editInfo = false;
          this.sharedService.updateReportStatusAndEmit(this.route);
          this.router.navigate([this.controlPathUrl]);
        });
    }

  }

  onCancel() {
    this.editInfo = false;
    this.router.navigate([this.controlPathUrl]);
  }

  canDeactivate(): Promise<boolean> | boolean {
    // Allow synchronous navigation (`true`) if both forms are clean
    if (!this.editInfo || !this.controlPathComponent.controlPathForm.dirty) {
        return true;
    }
    // Otherwise ask the user with the dialog service and return its
    // promise which resolves to true or false when the user decides
    const modalMessage = 'There are unsaved edits on the screen. Leaving without saving will discard any changes. Are you sure you want to continue?';
    const modalRef = this.modalService.open(ConfirmationDialogComponent);
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.title = 'Unsaved Changes';
    modalRef.componentInstance.confirmButtonText = 'Confirm';
    return modalRef.result;
  }

  @HostListener('window:beforeunload', ['$event'])
  beforeunloadHandler(event) {
    if (this.editInfo && this.controlPathComponent.controlPathForm.dirty) {
      event.preventDefault();
      event.returnValue = '';
    }
    return true;
  }

}
