import { Component, OnInit, Input } from '@angular/core';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

@Component({
  selector: 'app-facility-list',
  templateUrl: './facility-list.component.html',
  styleUrls: ['./facility-list.component.scss']
})
export class FacilityListComponent implements OnInit {
  @Input() facilities: MasterFacilityRecord[];

  constructor() { }

  ngOnInit() {
  }

}
