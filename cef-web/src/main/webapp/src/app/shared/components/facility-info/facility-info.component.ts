import { Component, OnInit, Input } from '@angular/core';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';

@Component({
  selector: 'app-facility-info',
  templateUrl: './facility-info.component.html',
  styleUrls: ['./facility-info.component.scss']
})
export class FacilityInfoComponent implements OnInit {
  @Input() facility: CdxFacility;

  constructor() { }

  ngOnInit() {
  }

}
