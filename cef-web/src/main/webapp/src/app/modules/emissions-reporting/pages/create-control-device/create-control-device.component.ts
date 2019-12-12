import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { EditControlDeviceInfoPanelComponent } from '../../components/edit-control-device-info-panel/edit-control-device-info-panel.component';
import { ControlService } from 'src/app/core/services/control.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { Control } from 'src/app/shared/models/control';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-create-control-device',
  templateUrl: './create-control-device.component.html',
  styleUrls: ['./create-control-device.component.scss']
})
export class CreateControlDeviceComponent implements OnInit {
  @Input() facilitySite: FacilitySite;

  controlUrl: string;

  @ViewChild(EditControlDeviceInfoPanelComponent, { static: true })
  private controlDeviceComponent: EditControlDeviceInfoPanelComponent;

  constructor(
    private controlService: ControlService,
    private route: ActivatedRoute,
    private router: Router,
    private sharedService: SharedService) { }

  ngOnInit() {

    this.route.data
      .subscribe(data => {
        this.facilitySite = data.facilitySite;
      });

    this.route.paramMap
      .subscribe(params => {
        this.controlUrl = `/facility/${params.get('facilityId')}/report/${params.get('reportId')}/${BaseReportUrl.CONTROL_DEVICE}`;
      });

  }

  isValid() {
    return this.controlDeviceComponent.controlDeviceForm.valid;
  }

  onSubmit() {

    if (!this.isValid()) {
      this.controlDeviceComponent.controlDeviceForm.markAllAsTouched();
    } else {
      const saveControlDevice = new Control();

      Object.assign(saveControlDevice, this.controlDeviceComponent.controlDeviceForm.value);
      saveControlDevice.facilitySiteId = this.facilitySite.id;

      this.controlService.create(saveControlDevice)
        .subscribe(() => {
          this.sharedService.updateReportStatusAndEmit(this.route);
          this.router.navigate([this.controlUrl]);
        });
    }

  }
}
