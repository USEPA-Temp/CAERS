import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from "src/app/core/services/shared.service";
import { EmissionsReport } from "src/app/shared/models/emissions-report";

@Component({
  selector: 'app-emission-unit-dashboard',
  templateUrl: './emission-unit-dashboard.component.html',
  styleUrls: ['./emission-unit-dashboard.component.scss']
})
export class EmissionUnitDashboardComponent implements OnInit {
  emissionUnit: EmissionUnit;

  constructor(private emissionUnitService: EmissionUnitService, private route: ActivatedRoute, private  sharedService: SharedService) { }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.emissionUnitService.retrieve(+map.get('unitId'))
        .subscribe(unit => {
          console.log('emission unit', unit);
          this.emissionUnit = unit;
        });
    });
    //emits the report info to the sidebar
    this.route.data
    .subscribe((data: { emissionsReport: EmissionsReport }) => {
        this.sharedService.emitChange(data.emissionsReport);
      });
  }

}
