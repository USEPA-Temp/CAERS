import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ControlPathService } from 'src/app/core/services/control-path.service';
import { ActivatedRoute } from '@angular/router';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { EditControlPathInfoPanelComponent } from '../../components/edit-control-path-info-panel/edit-control-path-info-panel.component';
import { SharedService } from 'src/app/core/services/shared.service';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';

@Component({
  selector: 'app-control-path-details',
  templateUrl: './control-path-details.component.html',
  styleUrls: ['./control-path-details.component.scss']
})
export class ControlPathDetailsComponent implements OnInit {
  @Input() controlPath: ControlPath;
  controlPathAssignments: ControlAssignment[];
  editInfo = false;
  readOnlyMode = true;
  facilitySite: FacilitySite;

  @ViewChild(EditControlPathInfoPanelComponent, { static: false })
  private controlPathComponent: EditControlPathInfoPanelComponent;

  constructor(private controlPathService: ControlPathService,
              private route: ActivatedRoute,
              private sharedService: SharedService) { }

  ngOnInit() {
    this.route.paramMap
    .subscribe(map => {
      this.controlPathService.retrieve(+map.get('controlPathId'))
      .subscribe(controlPath => {
        this.controlPath = controlPath;
        this.controlPathService.retrieveAssignmentsForControlPath(this.controlPath.id)
        .subscribe(assignments => {
          this.controlPathAssignments = assignments;
        });
      });
    });

    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.facilitySite = data.facilitySite;
      this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;
    });


  }

  setEditInfo(value: boolean) {
    this.editInfo = value;
  }

    updateControlPath() {
      if (!this.controlPathComponent.controlPathForm.valid) {
        this.controlPathComponent.controlPathForm.markAllAsTouched();
      } else {
        const updatedControlPath = new ControlPath();

        Object.assign(updatedControlPath, this.controlPathComponent.controlPathForm.value);
        updatedControlPath.id = this.controlPath.id;

        this.controlPathService.update(updatedControlPath)
        .subscribe(result => {

          Object.assign(this.controlPath, result);
          this.sharedService.updateReportStatusAndEmit(this.route);
          this.setEditInfo(false);
        });
      }
  }
}
