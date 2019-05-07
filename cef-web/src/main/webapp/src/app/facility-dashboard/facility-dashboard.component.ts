import { Component, OnInit } from '@angular/core';
import { Facility } from '../model/facility';
import { FacilityService } from '../services/facility.service';
import { UserContextService } from '../services/user-context.service';

@Component({
  selector: 'app-facility-dashboard',
  templateUrl: './facility-dashboard.component.html',
  styleUrls: ['./facility-dashboard.component.scss']
})
export class FacilityDashboardComponent implements OnInit {
  facilities: Facility[] = [];

  constructor(private facilityService: FacilityService, private userContext: UserContextService) { }

  ngOnInit() {
    this.getFacilities();
    console.log(this);
  }

  getFacilities(): void {
    this.facilityService.getMyFacilities()
    .subscribe(facilities => this.facilities = facilities);
  }

}
