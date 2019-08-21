import { FacilitySite } from 'src/app/shared/models/facility-site';
import { Process } from 'src/app/shared/models/process';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { ControlAssignmentService } from 'src/app/core/services/control-assignment.service';

@Component({
  selector: 'app-release-point-details',
  templateUrl: './release-point-details.component.html',
  styleUrls: ['./release-point-details.component.scss']
})
export class ReleasePointDetailsComponent implements OnInit {
  releasePoint: ReleasePoint;
  processes: Process[];
  controlAssignments: ControlAssignment[];
  parentComponentType = 'releasePointAppt';

  constructor(
    private releasePointService: ReleasePointService,
    private processService: EmissionsProcessService,
    private controlAssignmentService: ControlAssignmentService,
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

        this.controlAssignmentService.retrieveForReleasePoint(+map.get('releasePointId'))
        .subscribe(controlAssignments => {
          this.controlAssignments = controlAssignments;
        });
    });

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
        this.sharedService.emitChange(data.facilitySite);
      });
  }

}
