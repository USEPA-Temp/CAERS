import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { FacilitySiteContact } from 'src/app/shared/models/facility-site-contact';
import { FacilitySiteContactService } from 'src/app/core/services/facility-site-contact.service';
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
    private contactService: FacilitySiteContactService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    // get the url params
    this.route.paramMap
      .subscribe(map => {
        // retrieve the facility site for this url
        this.facilitySiteService.retrieveForReport(map.get('facilityId'), +map.get('reportId'))
        .subscribe(site => {
          this.facilitySite = site;
          // after facility site is loaded, retrieve contacts for it
          this.contactService.retrieveForFacility(this.facilitySite.id)
          .subscribe(contacts => {
            this.facilitySite.contacts = contacts;
          });
        });
    });
  }
}
