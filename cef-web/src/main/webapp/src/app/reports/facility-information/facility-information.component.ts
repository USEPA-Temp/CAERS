import { FacilitySite } from 'src/app/model/facility-site';
import { FacilitySiteService } from 'src/app/services/facility-site.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-facility-information',
  templateUrl: './facility-information.component.html',
  styleUrls: ['./facility-information.component.scss']
})
export class FacilityInformationComponent implements OnInit {
  facilitySite: FacilitySite;

  constructor(
    private facilitySiteService: FacilitySiteService,
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
        });
    });
  }
}
