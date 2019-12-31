import { FacilitySite } from 'src/app/shared/models/facility-site';
import { Process } from 'src/app/shared/models/process';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ControlPathService } from 'src/app/core/services/control-path.service';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { EditReleasePointPanelComponent } from '../../components/edit-release-point-panel/edit-release-point-panel.component';

@Component({
  selector: 'app-release-point-details',
  templateUrl: './release-point-details.component.html',
  styleUrls: ['./release-point-details.component.scss']
})
export class ReleasePointDetailsComponent implements OnInit {
  @Input() releasePoint: ReleasePoint;
  processes: Process[];
  controlPaths: ControlPath[];
  parentComponentType = 'releasePointAppt';

  fugitive = false;
  readOnlyMode = true;
  editInfo = false;

  @ViewChild(EditReleasePointPanelComponent, { static: false })
  private releasePointComponent: EditReleasePointPanelComponent;

  constructor(
    private releasePointService: ReleasePointService,
    private processService: EmissionsProcessService,
    private controlPathService: ControlPathService,
    private route: ActivatedRoute,
    private sharedService: SharedService) { }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.releasePointService.retrieve(+map.get('releasePointId'))
        .subscribe(point => {
          this.releasePoint = point;

          if (this.releasePoint.typeCode.description === 'Fugitive') {
            this.fugitive = true;
          }

          this.processService.retrieveForReleasePoint(this.releasePoint.id)
          .subscribe(processes => {
            this.processes = processes;
          });
        });

        this.controlPathService.retrieveForReleasePoint(+map.get('releasePointId'))
        .subscribe(controlPaths => {
          this.controlPaths = controlPaths;
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

  updateReleasePoint() {
    if (!this.releasePointComponent.releasePointForm.valid) {
      this.releasePointComponent.releasePointForm.markAllAsTouched();
    } else {
      const updatedReleasePoint = new ReleasePoint();

      Object.assign(updatedReleasePoint, this.releasePointComponent.releasePointForm.value);
      updatedReleasePoint.id = this.releasePoint.id;

      this.releasePointService.update(updatedReleasePoint)
      .subscribe(result => {

        if (updatedReleasePoint.typeCode.description === 'Fugitive') {
          this.fugitive = true;
        } else {
          this.fugitive = false;
        }

        Object.assign(this.releasePoint, result);
        this.sharedService.updateReportStatusAndEmit(this.route);
        this.setEditInfo(false);
      });
    }
  }

}
