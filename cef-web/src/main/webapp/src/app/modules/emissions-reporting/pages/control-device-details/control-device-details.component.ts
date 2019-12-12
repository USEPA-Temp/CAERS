import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { ControlService } from 'src/app/core/services/control.service';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { Control } from 'src/app/shared/models/control';
import { EmissionsReportItem } from 'src/app/shared/models/emissions-report-item';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { EditControlDeviceInfoPanelComponent } from '../../components/edit-control-device-info-panel/edit-control-device-info-panel.component';

@Component({
  selector: 'app-control-device-details',
  templateUrl: './control-device-details.component.html',
  styleUrls: ['./control-device-details.component.scss']
})
export class ControlDeviceDetailsComponent implements OnInit {
  @Input() control: Control;
  emissionsReportItems: EmissionsReportItem[];

  editInfo = false;
  readOnlyMode = true;

  @ViewChild(EditControlDeviceInfoPanelComponent, { static: false })
  private controlComponent: EditControlDeviceInfoPanelComponent;

  constructor(
    private controlService: ControlService,
    private route: ActivatedRoute,
    private sharedService: SharedService) { }

  ngOnInit() {
    this.route.paramMap
    .subscribe(map => {
      this.controlService.retrieve(+map.get('controlId'))
      .subscribe(control => {
        this.control = control;
        this.control.pollutants = control.pollutants.sort((a, b) => (a.pollutant.pollutantName > b.pollutant.pollutantName ? 1 : -1));
      });

      this.controlService.retrieveComponents(+map.get('controlId'))
      .subscribe(emissionsReportItems => {
        this.emissionsReportItems = emissionsReportItems.sort((a, b) => (a.identifier > b.identifier ? 1 : -1));
      });
    });

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;

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
