import { FacilityContextService } from '../../services/facility-context.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  constructor(public facilityContext: FacilityContextService) { }

  ngOnInit() {
  }

}
