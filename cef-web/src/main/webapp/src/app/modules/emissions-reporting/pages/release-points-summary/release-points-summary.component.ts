import { Component, OnInit } from '@angular/core';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-release-points-summary',
  templateUrl: './release-points-summary.component.html',
  styleUrls: ['./release-points-summary.component.scss']
})
export class ReleasePointsSummaryComponent implements OnInit {
  facilitySite: FacilitySite;
  releasePoints: ReleasePoint[];

  constructor(
    private releasePointService: ReleasePointService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    // get the resolved facilitySite
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {

      this.facilitySite = data.facilitySite;

      this.releasePointService.retrieveForFacility(this.facilitySite.id)
      .subscribe(points => {
        this.releasePoints = points;
      });
    });
  }

}