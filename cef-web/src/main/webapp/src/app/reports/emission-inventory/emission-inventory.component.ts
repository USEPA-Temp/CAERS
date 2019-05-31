import { EmissionsReport } from '../../model/emissions-report';
import { FacilitySite } from '../../model/facility-site';
import { FacilityService } from '../../services/facility.service';
import { FacilitySiteService } from '../../services/facility-site.service';
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
    private facilityService: FacilityService,
    private facilitySiteService: FacilitySiteService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.facilityService.retrieveFacilitySite(map.get('facilityId'), +map.get('reportId'))
        .subscribe(site => {
          console.log('facilitySite', site);
          this.facilitySite = site;
          this.facilitySiteService.retrieveEmissionUnits(this.facilitySite.id)
          .subscribe(units => {
            this.facilitySite.emissionsUnits = units;
          });
          this.facilitySiteService.retrieveReleasePoints(this.facilitySite.id)
          .subscribe(points => {
            this.facilitySite.releasePoints = points;
          });
        });
    });
  }

}
