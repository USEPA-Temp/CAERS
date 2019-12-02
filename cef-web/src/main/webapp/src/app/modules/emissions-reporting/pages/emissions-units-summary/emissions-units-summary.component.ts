import { Component, OnInit } from '@angular/core';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-emissions-units-summary',
  templateUrl: './emissions-units-summary.component.html',
  styleUrls: ['./emissions-units-summary.component.scss']
})
export class EmissionsUnitsSummaryComponent implements OnInit {
  facilitySite: FacilitySite;
  emissionsUnits: EmissionUnit[];

  constructor(
    private emissionUnitsService: EmissionUnitService,
    private route: ActivatedRoute,
    private sharedService: SharedService) { }

  ngOnInit() {
    // get the resolved facilitySite
    this.route.data
      .subscribe((data: { facilitySite: FacilitySite }) => {

      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);

      this.emissionUnitsService.retrieveForFacility(this.facilitySite.id)
        .subscribe(units => {
          this.emissionsUnits = units.sort((a, b) => (a.unitIdentifier > b.unitIdentifier ? 1 : -1));
      });
    });
  }

}
