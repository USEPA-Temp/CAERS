import { Component, OnInit } from '@angular/core';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { CdxFacilityService } from 'src/app/core/services/cdx-facility.service';
import { UserContextService } from 'src/app/core/services/user-context.service';

@Component({
  selector: 'app-facility-dashboard',
  templateUrl: './facility-dashboard.component.html',
  styleUrls: ['./facility-dashboard.component.scss']
})
export class FacilityDashboardComponent implements OnInit {
  facilities: CdxFacility[] = [];

  constructor(private cdxFacilityService: CdxFacilityService, private userContext: UserContextService) { }

  ngOnInit() {
    this.getFacilities();
    console.log(this);
  }

  getFacilities(): void {
    this.cdxFacilityService.getMyFacilities()
    .subscribe(facilities => 
      this.facilities = facilities.sort((a, b) => (a.facilityName > b.facilityName) ? 1 : -1)
      );
  }

}
