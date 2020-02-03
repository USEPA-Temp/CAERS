import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { SharedService } from 'src/app/core/services/shared.service';
import { ControlPathService } from 'src/app/core/services/control-path.service';
import { ControlPath } from 'src/app/shared/models/control-path';

@Component({
  selector: 'app-control-paths-summary',
  templateUrl: './control-paths-summary.component.html',
  styleUrls: ['./control-paths-summary.component.scss']
})
export class ControlPathsSummaryComponent implements OnInit {
  facilitySite: FacilitySite;
  controlPaths: ControlPath[];

  constructor(private route: ActivatedRoute,
              private sharedService: SharedService,
              private controlPathService: ControlPathService) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);
      this.controlPathService.retrieveForFacilitySite(this.facilitySite.id)
      .subscribe(controls => {
        this.controlPaths = controls;
      });
    });
  }

}
