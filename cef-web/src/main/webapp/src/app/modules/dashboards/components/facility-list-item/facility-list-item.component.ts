import { Component, OnInit, Input } from '@angular/core';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

@Component({
  selector: 'app-facility-list-item',
  templateUrl: './facility-list-item.component.html',
  styleUrls: ['./facility-list-item.component.scss']
})
export class FacilityListItemComponent implements OnInit {
  @Input() facility: MasterFacilityRecord;

  constructor() { }

  ngOnInit() {
  }

}
