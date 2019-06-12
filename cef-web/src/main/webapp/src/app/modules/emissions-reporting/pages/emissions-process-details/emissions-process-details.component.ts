import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { Process } from 'src/app/shared/models/process';
import { SharedService } from 'src/app/core/services/shared.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';

@Component({
  selector: 'app-emissions-process-details',
  templateUrl: './emissions-process-details.component.html',
  styleUrls: ['./emissions-process-details.component.scss']
})
export class EmissionsProcessDetailsComponent implements OnInit {
  process: Process;

  constructor(
    private processService: EmissionsProcessService,
    private route: ActivatedRoute,
    private sharedService: SharedService) { }

  ngOnInit() {
    this.route.paramMap
    .subscribe(map => {
      this.processService.retrieve(+map.get('processId'))
      .subscribe(process => {
        this.process = process;
      });
    });

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.sharedService.emitChange(data.facilitySite);
    });
  }

}
