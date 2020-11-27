import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { ControlService } from 'src/app/core/services/control.service';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { Control } from 'src/app/shared/models/control';
import { EmissionsReportItem } from 'src/app/shared/models/emissions-report-item';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { EditControlDeviceInfoPanelComponent } from '../../components/edit-control-device-info-panel/edit-control-device-info-panel.component';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ControlPathService } from 'src/app/core/services/control-path.service';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';

@Component({
  selector: 'app-control-device-details',
  templateUrl: './control-device-details.component.html',
  styleUrls: ['./control-device-details.component.scss']
})
export class ControlDeviceDetailsComponent implements OnInit {
  @Input() control: Control;
  emissionsReportItems: EmissionsReportItem[];
  facilitySite: FacilitySite;
  controlPaths: ControlPath[];
  baseUrl: string;
  editInfo = false;
  readOnlyMode = true;

  @ViewChild(EditControlDeviceInfoPanelComponent)
  private controlComponent: EditControlDeviceInfoPanelComponent;

  constructor(
    private controlService: ControlService,
    private route: ActivatedRoute,
    private userContextService: UserContextService,
    private sharedService: SharedService,
    private controlPathService: ControlPathService) { }

  ngOnInit() {
    this.route.paramMap
    .subscribe(map => {
      this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}/${BaseReportUrl.CONTROL_PATH}`;
      this.controlService.retrieve(+map.get('controlId'))
      .subscribe(control => {
        this.control = control;
        this.control.pollutants = control.pollutants.sort((a, b) => (a.pollutant.pollutantName > b.pollutant.pollutantName ? 1 : -1));
      });

      this.controlPathService.retrieveForControlDevice(+map.get('controlId')).subscribe((controlPaths) => {
        this.controlPaths = controlPaths;
      });

      this.controlService.retrieveComponents(+map.get('controlId'))
      .subscribe(emissionsReportItems => {
        this.emissionsReportItems = emissionsReportItems.sort((a, b) => (a.identifier > b.identifier ? 1 : -1));
      });
    });

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.userContextService.getUser().subscribe( user => {
        if (user.role !== 'Reviewer' && ReportStatus.IN_PROGRESS === data.facilitySite.emissionsReport.status) {
          this.readOnlyMode = false;
        }
      });
      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);
    });
  }

  setEditInfo(value: boolean) {
    this.editInfo = value;
  }

  updateControl() {
    if (!this.controlComponent.controlDeviceForm.valid) {
      this.controlComponent.controlDeviceForm.markAllAsTouched();
    } else {
      const updatedControl = new Control();

      Object.assign(updatedControl, this.controlComponent.controlDeviceForm.value);
      updatedControl.id = this.control.id;

      this.controlService.update(updatedControl)
      .subscribe(result => {

        Object.assign(this.control, result);
        this.sharedService.updateReportStatusAndEmit(this.route);
        this.setEditInfo(false);
      });
    }
  }
}
