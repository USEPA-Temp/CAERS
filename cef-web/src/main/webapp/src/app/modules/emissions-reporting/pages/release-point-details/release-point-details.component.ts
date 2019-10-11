import { FacilitySite } from 'src/app/shared/models/facility-site';
import { Process } from 'src/app/shared/models/process';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ControlPathService } from 'src/app/core/services/control-path.service';

@Component({
  selector: 'app-release-point-details',
  templateUrl: './release-point-details.component.html',
  styleUrls: ['./release-point-details.component.scss']
})
export class ReleasePointDetailsComponent implements OnInit {
  releasePoint: ReleasePoint;
  processes: Process[];
  controlPaths: ControlPath[];
  parentComponentType = 'releasePointAppt';

  readOnlyMode = true;

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
          console.log('releasePoint', point);
          this.releasePoint = point;
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

}
