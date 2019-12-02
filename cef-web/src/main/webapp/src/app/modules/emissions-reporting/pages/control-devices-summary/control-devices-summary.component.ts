import { Component, OnInit } from '@angular/core';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { Control } from 'src/app/shared/models/control';
import { ControlService } from 'src/app/core/services/control.service';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-control-devices-summary',
  templateUrl: './control-devices-summary.component.html',
  styleUrls: ['./control-devices-summary.component.scss']
})
export class ControlDevicesSummaryComponent implements OnInit {
  facilitySite: FacilitySite;
  controls: Control[];

  constructor(
    private controlService: ControlService,
    private route: ActivatedRoute,
    private sharedService: SharedService) { }

  ngOnInit() {
    // get the resolved facilitySite
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {

      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);

      this.controlService.retrieveForFacilitySite(this.facilitySite.id)
      .subscribe(controls => {
        this.controls = controls;
      });
    });
  }

}
