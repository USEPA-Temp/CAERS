import { Component, OnInit, Input } from '@angular/core';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

@Component({
  selector: 'app-facility-info',
  templateUrl: './facility-info.component.html',
  styleUrls: ['./facility-info.component.scss']
})
export class FacilityInfoComponent implements OnInit {
  @Input() facility: MasterFacilityRecord;

  constructor() { }

  ngOnInit() {
  }

}
