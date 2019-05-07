import { Component, OnInit, Input } from '@angular/core';
import { Facility } from '../../model/facility';

@Component({
  selector: 'app-facility-info',
  templateUrl: './facility-info.component.html',
  styleUrls: ['./facility-info.component.scss']
})
export class FacilityInfoComponent implements OnInit {
  @Input() facility: Facility;

  constructor() { }

  ngOnInit() {
  }

}
