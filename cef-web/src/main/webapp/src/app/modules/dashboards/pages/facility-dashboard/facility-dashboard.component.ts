import { Component, OnInit } from '@angular/core';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { UserFacilityAssociationService } from 'src/app/core/services/user-facility-association.service';

@Component({
  selector: 'app-facility-dashboard',
  templateUrl: './facility-dashboard.component.html',
  styleUrls: ['./facility-dashboard.component.scss']
})
export class FacilityDashboardComponent implements OnInit {
  facilities: MasterFacilityRecord[] = [];

  constructor( private ufaService: UserFacilityAssociationService, private userContext: UserContextService) { }

  ngOnInit() {
    this.getFacilities();
    //console.log(this);
  }

  getFacilities(): void {
    this.ufaService.getMyAssociations()
    .subscribe(associations => {
      this.facilities = associations.filter(a => a.approved)
                                    .map(a => a.masterFacilityRecord)
                                    .sort((a, b) => (a.name > b.name) ? 1 : -1);
    });
  }

}
