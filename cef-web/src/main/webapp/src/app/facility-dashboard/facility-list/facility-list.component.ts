import { Facility } from '../../model/facility';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-facility-list',
  templateUrl: './facility-list.component.html',
  styleUrls: ['./facility-list.component.scss']
})
export class FacilityListComponent implements OnInit {
  @Input() facilities: Facility[];

  constructor() { }

  ngOnInit() {
  }

}
