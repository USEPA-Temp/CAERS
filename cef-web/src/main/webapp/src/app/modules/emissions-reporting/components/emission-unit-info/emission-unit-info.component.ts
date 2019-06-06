import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-emission-unit-info',
  templateUrl: './emission-unit-info.component.html',
  styleUrls: ['./emission-unit-info.component.scss']
})
export class EmissionUnitInfoComponent implements OnInit {
  @Input() emissionUnit: EmissionUnit;

  constructor() { }

  ngOnInit() {
  }

}
