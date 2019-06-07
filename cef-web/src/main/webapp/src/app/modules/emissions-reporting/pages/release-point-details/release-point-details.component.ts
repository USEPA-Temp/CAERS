import { Process } from 'src/app/shared/models/process';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsReport } from "src/app/shared/models/emissions-report";
import { SharedService } from "src/app/core/services/shared.service";

@Component({
  selector: 'app-release-point-details',
  templateUrl: './release-point-details.component.html',
  styleUrls: ['./release-point-details.component.scss']
})
export class ReleasePointDetailsComponent implements OnInit {
  releasePoint: ReleasePoint;
  processes: Process[];

  constructor(
    private releasePointService: ReleasePointService,
    private processService: EmissionsProcessService,
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
    });
    
    //emits the report info to the sidebar
    this.route.data
    .subscribe((data: { emissionsReport: EmissionsReport }) => {
        this.sharedService.emitChange(data.emissionsReport);
      });
  }

}
