import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-emission-process-list',
  templateUrl: './emission-process-list.component.html',
  styleUrls: ['./emission-process-list.component.scss']
})
export class EmissionProcessListComponent implements OnInit {
  @Input() emissionUnit: EmissionUnit;

  constructor() { }

  ngOnInit() {
  }

}
