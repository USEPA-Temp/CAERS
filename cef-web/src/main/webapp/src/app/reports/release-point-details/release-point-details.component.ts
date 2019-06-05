import { Process } from '../model/process';
import { ReleasePoint } from '../model/release-point';
import { EmissionsProcessService } from '../services/emissions-process.service';
import { ReleasePointService } from '../services/release-point.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

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
    private route: ActivatedRoute) { }

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
  }

}
