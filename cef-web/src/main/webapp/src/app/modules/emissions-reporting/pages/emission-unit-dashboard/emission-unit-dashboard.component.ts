import { FacilitySite } from 'src/app/shared/models/facility-site';
import { Process } from 'src/app/shared/models/process';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ControlPathService } from 'src/app/core/services/control-path.service';


@Component({
  selector: 'app-emission-unit-dashboard',
  templateUrl: './emission-unit-dashboard.component.html',
  styleUrls: ['./emission-unit-dashboard.component.scss']
})
export class EmissionUnitDashboardComponent implements OnInit {
  emissionsUnit: EmissionUnit;
  processes: Process[];
  controlPaths: ControlPath[];

  readOnlyMode = true;

  constructor(
    private emissionUnitService: EmissionUnitService,
    private processService: EmissionsProcessService,
    private controlPathService: ControlPathService,
    private route: ActivatedRoute,
    private sharedService: SharedService) { }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.emissionUnitService.retrieve(+map.get('unitId'))
        .subscribe(unit => {
          console.log('emission unit2', unit);
          this.emissionsUnit = unit;
          this.processService.retrieveForEmissionsUnit(this.emissionsUnit.id)
          .subscribe(processes => {
            console.log('processes: ', processes);
            this.processes = processes;
          });
        });

        this.controlPathService.retrieveForEmissionsUnit(+map.get('unitId'))
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
