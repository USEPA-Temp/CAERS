import { Component, OnInit } from '@angular/core';
import { ControlService } from 'src/app/core/services/control.service';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { Control } from 'src/app/shared/models/control';
import { FacilitySite } from 'src/app/shared/models/facility-site';

@Component({
  selector: 'app-control-device-details',
  templateUrl: './control-device-details.component.html',
  styleUrls: ['./control-device-details.component.scss']
})
export class ControlDeviceDetailsComponent implements OnInit {
  control: Control;

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
      });
    });

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.sharedService.emitChange(data.facilitySite);
    });
  }

}