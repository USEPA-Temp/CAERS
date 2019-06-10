import { FacilitySite } from 'src/app/shared/models/facility-site';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-emission-inventory',
  templateUrl: './emission-inventory.component.html',
  styleUrls: ['./emission-inventory.component.scss']
})
export class EmissionInventoryComponent implements OnInit {
  facilitySite: FacilitySite;

  constructor(
    private emissionUnitsService: EmissionUnitService,
    private releasePointService: ReleasePointService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    // get the resolved facilitySite
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {

      this.facilitySite = data.facilitySite;
      // after facility site is loaded, retrieve emission units and release points for it
      this.emissionUnitsService.retrieveForFacility(this.facilitySite.id)
      .subscribe(units => {
        this.facilitySite.emissionsUnits = units;
      });
      this.releasePointService.retrieveForFacility(this.facilitySite.id)
      .subscribe(points => {
        this.facilitySite.releasePoints = points;
      });
    });
  }

}
