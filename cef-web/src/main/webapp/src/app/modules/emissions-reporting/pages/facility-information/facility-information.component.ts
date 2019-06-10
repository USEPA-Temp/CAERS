import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { FacilitySiteContact } from 'src/app/shared/models/facility-site-contact';
import { FacilitySiteContactService } from 'src/app/core/services/facility-site-contact.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsReport } from "src/app/shared/models/emissions-report";
import { SharedService } from "src/app/core/services/shared.service";

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
    });
  }

}
