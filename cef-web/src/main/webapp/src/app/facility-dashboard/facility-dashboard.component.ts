import { Component, OnInit } from '@angular/core';
import { Facility } from '../model/facility';
import { FacilityService } from '../services/facility.service';

@Component({
  selector: 'app-facility-dashboard',
  templateUrl: './facility-dashboard.component.html',
  styleUrls: ['./facility-dashboard.component.css']
})
export class FacilityDashboardComponent implements OnInit {
  facilities: Facility[] = [];

  constructor(private facilityService: FacilityService) { }

  ngOnInit() {
    this.getFacilities();
    console.log(this);
  }

  getFacilities(): void {
    this.facilityService.getFacilities()
    .subscribe(facilities => this.facilities = facilities);
  }

}
