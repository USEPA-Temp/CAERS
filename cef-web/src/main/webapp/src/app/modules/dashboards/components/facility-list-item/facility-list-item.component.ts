import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-facility-list-item',
  templateUrl: './facility-list-item.component.html',
  styleUrls: ['./facility-list-item.component.scss']
})
export class FacilityListItemComponent implements OnInit {
  @Input() facility: CdxFacility;

  constructor() { }

  ngOnInit() {
  }

}
