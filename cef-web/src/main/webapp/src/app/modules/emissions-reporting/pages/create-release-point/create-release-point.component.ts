import { Component, OnInit, ViewChild, Input, HostListener } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EditReleasePointPanelComponent } from '../../components/edit-release-point-panel/edit-release-point-panel.component';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { SharedService } from 'src/app/core/services/shared.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-create-release-point',
  templateUrl: './create-release-point.component.html',
  styleUrls: ['./create-release-point.component.scss']
})
export class CreateReleasePointComponent implements OnInit {
  @Input() facilitySite: FacilitySite;

  releaseUrl: string;
  editInfo = true;

  @ViewChild(EditReleasePointPanelComponent, { static: true })
  private releasePointComponent: EditReleasePointPanelComponent;

  constructor(
    private releasePointService: ReleasePointService,
    private route: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private sharedService: SharedService) { }

  ngOnInit() {

    this.route.data
    .subscribe(data => {
      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);
    });

    this.route.paramMap
    .subscribe(params => {
      this.releaseUrl = `/facility/${params.get('facilityId')}/report/${params.get('reportId')}/${BaseReportUrl.RELEASE_POINT}`;
    });

  }

  isValid() {
    return this.releasePointComponent.releasePointForm.valid;
  }

  onSubmit() {

    if (!this.isValid()) {
      this.releasePointComponent.releasePointForm.markAllAsTouched();
    } else {
      const saveReleasePoint = new ReleasePoint();

      Object.assign(saveReleasePoint, this.releasePointComponent.releasePointForm.value);
      saveReleasePoint.facilitySiteId = this.facilitySite.id;
      saveReleasePoint.releasePointIdentifier = this.releasePointComponent.releasePointForm.controls.releasePointIdentifier.value.trim();

      this.releasePointService.create(saveReleasePoint)
      .subscribe(() => {
        this.editInfo = false;
        this.sharedService.updateReportStatusAndEmit(this.route);
        this.router.navigate([this.releaseUrl]);
      });
    }

  }

  onCancel() {
    this.editInfo = false;
    this.router.navigate([this.releaseUrl]);
  }

  canDeactivate(): Promise<boolean> | boolean {
    // Allow synchronous navigation (`true`) if both forms are clean
    if (!this.editInfo || !this.releasePointComponent.releasePointForm.dirty) {
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
    if (this.editInfo && this.releasePointComponent.releasePointForm.dirty) {
      event.preventDefault();
      event.returnValue = '';
    }
    return true;
  }

}
