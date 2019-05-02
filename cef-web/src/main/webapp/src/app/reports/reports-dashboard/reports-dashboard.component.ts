import { Facility } from '../../model/facility';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-reports-dashboard',
  templateUrl: './reports-dashboard.component.html',
  styleUrls: ['./reports-dashboard.component.css']
})
export class ReportsDashboardComponent implements OnInit {
  facility: Facility;

  constructor() { }

  ngOnInit() {
  }

}
