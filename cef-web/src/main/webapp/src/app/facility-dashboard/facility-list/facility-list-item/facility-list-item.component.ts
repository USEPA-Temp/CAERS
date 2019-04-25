import { Facility } from '../../../model/facility';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-facility-list-item',
  templateUrl: './facility-list-item.component.html',
  styleUrls: ['./facility-list-item.component.css']
})
export class FacilityListItemComponent implements OnInit {
  @Input() facility: Facility;

  constructor() { }

  ngOnInit() {
  }

}
