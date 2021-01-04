import { Component, OnInit, Input } from '@angular/core';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

@Component({
  selector: 'app-master-facility-info',
  templateUrl: './master-facility-info.component.html',
  styleUrls: ['./master-facility-info.component.scss']
})
export class MasterFacilityInfoComponent implements OnInit {
  @Input() facility: MasterFacilityRecord;

  constructor() { }

  ngOnInit(): void {
  }

}
