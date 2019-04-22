import { Component, OnInit } from '@angular/core';
import { Facility } from '../model/facility';
import { FacilityService } from '../services/facility.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
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
