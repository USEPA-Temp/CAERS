import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FacilitySiteContactService } from 'src/app/core/services/facility-site-contact.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { FacilityNaicsCode } from 'src/app/shared/models/facility-naics-code';

@Component({
  selector: 'app-facility-information',
  templateUrl: './facility-information.component.html',
  styleUrls: ['./facility-information.component.scss']
})
export class FacilityInformationComponent implements OnInit {
  facilitySite: FacilitySite;
  naicsCodes: FacilityNaicsCode[];

  constructor(
    private contactService: FacilitySiteContactService,
    private sharedService: SharedService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {

      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);

      this.contactService.retrieveForFacility(this.facilitySite.id)
      .subscribe(contacts => {
        this.facilitySite.contacts = contacts;
      });

      this.naicsCodes = this.facilitySite.facilityNAICS;
      this.naicsCodes.sort((a, b) => a.primaryFlag && !b.primaryFlag ? -1 : !a.primaryFlag && b.primaryFlag ? 1 : 0);
    });
  }

}
