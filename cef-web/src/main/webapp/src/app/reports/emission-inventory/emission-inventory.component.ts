import { EmissionsReport } from '../../model/emissions-report';
import { FacilitySite } from '../../model/facility-site';
import { FacilityService } from '../../services/facility.service';
import { FacilitySiteService } from '../../services/facility-site.service';
import { EmissionUnitService } from '../services/emission-unit.service';
import { ReleasePointService } from '../services/release-point.service';
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
    private facilitySiteService: FacilitySiteService,
    private emissionUnitsService: EmissionUnitService,
    private releasePointService: ReleasePointService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    // get the url params
    this.route.paramMap
      .subscribe(map => {
        // retrieve the facility site for this url
        this.facilitySiteService.retrieveForReport(map.get('facilityId'), +map.get('reportId'))
        .subscribe(site => {
          console.log('facilitySite', site);
          this.facilitySite = site;
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
    });
  }

}
