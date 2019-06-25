import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { Process } from 'src/app/shared/models/process';
import { SharedService } from 'src/app/core/services/shared.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ReportingPeriodService } from 'src/app/core/services/reporting-period.service';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { ControlAssignmentService } from 'src/app/core/services/control-assignment.service';

@Component({
  selector: 'app-emissions-process-details',
  templateUrl: './emissions-process-details.component.html',
  styleUrls: ['./emissions-process-details.component.scss']
})
export class EmissionsProcessDetailsComponent implements OnInit {
  process: Process;
  controls: ControlAssignment[];

  constructor(
    private processService: EmissionsProcessService,
    private reportingPeriodService: ReportingPeriodService,
    private controlAssignmentService: ControlAssignmentService,
    private route: ActivatedRoute,
    private sharedService: SharedService) { }

  ngOnInit() {
    this.route.paramMap
    .subscribe(map => {
      this.processService.retrieve(+map.get('processId'))
      .subscribe(process => {
        this.process = process;
        this.reportingPeriodService.retrieveForEmissionsProcess(this.process.id)
        .subscribe(periods => {
          this.process.reportingPeriods = periods;
        });
      });

      this.controlAssignmentService.retrieveForEmissionsProcess(+map.get('processId'))
      .subscribe(controls => {
        this.controls = controls;
      });
    });

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.sharedService.emitChange(data.facilitySite);
    });
  }

}
