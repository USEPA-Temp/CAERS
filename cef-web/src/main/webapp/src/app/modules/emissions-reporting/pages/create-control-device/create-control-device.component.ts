import { Component, OnInit, Input, ViewChild, HostListener } from '@angular/core';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { EditControlDeviceInfoPanelComponent } from '../../components/edit-control-device-info-panel/edit-control-device-info-panel.component';
import { ControlService } from 'src/app/core/services/control.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { Control } from 'src/app/shared/models/control';
import { SharedService } from 'src/app/core/services/shared.service';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-create-control-device',
  templateUrl: './create-control-device.component.html',
  styleUrls: ['./create-control-device.component.scss']
})
export class CreateControlDeviceComponent implements OnInit {
  @Input() facilitySite: FacilitySite;

  controlUrl: string;
  editInfo = true;

  @ViewChild(EditControlDeviceInfoPanelComponent, { static: true })
  private controlDeviceComponent: EditControlDeviceInfoPanelComponent;

  constructor(
    private controlService: ControlService,
    private modalService: NgbModal,
    private route: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService) { }

  ngOnInit() {

    this.route.data
      .subscribe(data => {
        this.facilitySite = data.facilitySite;
        this.sharedService.emitChange(data.facilitySite);
      });

    this.route.paramMap
      .subscribe(params => {
        this.controlUrl = `/facility/${params.get('facilityId')}/report/${params.get('reportId')}/${BaseReportUrl.CONTROL_DEVICE}`;
      });

  }

  isValid() {
    return this.controlDeviceComponent.controlDeviceForm.valid;
  }

  transformDate(value): Date {
    if (value) {
        return new Date(value.year, value.month - 1, value.day);
    }
    return null;
  }

  onSubmit() {

    if (!this.isValid()) {
      this.controlDeviceComponent.controlDeviceForm.markAllAsTouched();
    } else {
      const saveControlDevice = new Control();

      Object.assign(saveControlDevice, this.controlDeviceComponent.controlDeviceForm.value);
      saveControlDevice.facilitySiteId = this.facilitySite.id;
      saveControlDevice.identifier = this.controlDeviceComponent.controlDeviceForm.controls.identifier.value.trim();
      saveControlDevice.startDate = this.transformDate(saveControlDevice.startDate);
      saveControlDevice.upgradeDate = this.transformDate(saveControlDevice.upgradeDate);
      saveControlDevice.endDate = this.transformDate(saveControlDevice.endDate);

      this.controlService.create(saveControlDevice)
        .subscribe(() => {
          this.editInfo = false;
          this.sharedService.updateReportStatusAndEmit(this.route);
          this.router.navigate([this.controlUrl]);
        });
    }

  }

  onCancel() {
    this.editInfo = false;
    this.router.navigate([this.controlUrl]);
  }

  canDeactivate(): Promise<boolean> | boolean {
    // Allow synchronous navigation (`true`) if both forms are clean
    if (!this.editInfo || !this.controlDeviceComponent.controlDeviceForm.dirty) {
        return true;
    }
    // Otherwise ask the user with the dialog service and return its
    // promise which resolves to true or false when the user decides
    const modalRef = this.modalService.open(ConfirmationDialogComponent);
    modalRef.componentInstance.message = 'There are unsaved edits on the screen. Leaving without saving will discard any changes. Are you sure you want to continue?';
    modalRef.componentInstance.title = 'Unsaved Changes';
    modalRef.componentInstance.confirmButtonText = 'Confirm';
    return modalRef.result;
  }

  @HostListener('window:beforeunload', ['$event'])
  beforeunloadHandler(event) {
    if (this.editInfo && this.controlDeviceComponent.controlDeviceForm.dirty) {
      event.preventDefault();
      event.returnValue = '';
    }
    return true;
  }
}
