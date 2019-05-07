import { Facility } from '../model/facility';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {
  facility: Facility;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { facility: Facility }) => {
      console.log(data.facility);
      this.facility = data.facility;
      });
  }

}
