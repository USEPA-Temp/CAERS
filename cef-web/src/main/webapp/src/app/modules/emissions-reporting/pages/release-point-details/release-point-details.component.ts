import { FacilitySite } from 'src/app/shared/models/facility-site';
import { Process } from 'src/app/shared/models/process';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ControlPathService } from 'src/app/core/services/control-path.service';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { EditReleasePointPanelComponent } from '../../components/edit-release-point-panel/edit-release-point-panel.component';
import { LookupService } from 'src/app/core/services/lookup.service';
import { ToastrService } from 'ngx-toastr';
import { EisLatLongToleranceLookup } from 'src/app/shared/models/eis-latlong-tolerance-lookup';

@Component({
  selector: 'app-release-point-details',
  templateUrl: './release-point-details.component.html',
  styleUrls: ['./release-point-details.component.scss']
})
export class ReleasePointDetailsComponent implements OnInit {
  @Input() releasePoint: ReleasePoint;
  processes: Process[];
  controlPaths: ControlPath[];
  facilitySite: FacilitySite;
  parentComponentType = 'releasePointAppt';
  eisProgramId: string;
  coordinateTolerance: EisLatLongToleranceLookup;
  factolerance: number;

  fugitive = false;
  readOnlyMode = true;
  editInfo = false;

  @ViewChild(EditReleasePointPanelComponent, { static: false })
  private releasePointComponent: EditReleasePointPanelComponent;

  constructor(
    private releasePointService: ReleasePointService,
    private processService: EmissionsProcessService,
    private controlPathService: ControlPathService,
    private lookupService: LookupService,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private sharedService: SharedService) { }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.releasePointService.retrieve(+map.get('releasePointId'))
        .subscribe(point => {
          this.releasePoint = point;

          if (this.releasePoint.typeCode.description === 'Fugitive') {
            this.fugitive = true;
          }

          this.processService.retrieveForReleasePoint(this.releasePoint.id)
          .subscribe(processes => {
            this.processes = processes;
          });
        });

        this.controlPathService.retrieveForReleasePoint(+map.get('releasePointId'))
        .subscribe(controlPaths => {
          this.controlPaths = controlPaths.sort((a, b) => (a.pathId > b.pathId) ? 1 : -1);
        });
    });

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;
      this.facilitySite = data.facilitySite;
      this.eisProgramId = data.facilitySite.eisProgramId;
      this.sharedService.emitChange(data.facilitySite);
    });

  }

  setEditInfo(value: boolean) {
    this.editInfo = value;
  }

  updateReleasePoint() {
    this.lookupService.retrieveLatLongTolerance(this.eisProgramId)
    .subscribe(result => {
      this.coordinateTolerance = result;

      if (!this.releasePointComponent.releasePointForm.valid || !this.checkCoordinateTolerance()) {
        this.releasePointComponent.releasePointForm.markAllAsTouched();
      } else {
        const updatedReleasePoint = new ReleasePoint();

        Object.assign(updatedReleasePoint, this.releasePointComponent.releasePointForm.value);
        updatedReleasePoint.id = this.releasePoint.id;

        this.releasePointService.update(updatedReleasePoint)
        .subscribe(result => {

          if (updatedReleasePoint.typeCode.description === 'Fugitive') {
            this.fugitive = true;
          } else {
            this.fugitive = false;
          }

          Object.assign(this.releasePoint, result);
          this.sharedService.updateReportStatusAndEmit(this.route);
          this.setEditInfo(false);
        });
      }
    });
  }

  checkCoordinateTolerance() {
    const rpLat = this.releasePointComponent.releasePointForm.get('latitude');
    const rpLong = this.releasePointComponent.releasePointForm.get('longitude');
    let inToleranceRange = true;

    if (this.coordinateTolerance === null) {
      this.factolerance = 0.003;
    } else {
      this.factolerance = this.coordinateTolerance.coordinateTolerance;
    }

    if ((rpLat !== null)
    && ((rpLat.value > this.facilitySite.latitude + this.factolerance)
    || (rpLat.value < this.facilitySite.latitude - this.factolerance))) {
      this.toastr.error('', 'Release Point latitude must within the ' + this.factolerance +
      ' tolerance range of Facility latitude coordinate ' + this.facilitySite.latitude + '.', {positionClass: 'toast-top-right'});
      this.releasePointComponent.releasePointForm.controls['latitude'].setErrors({'invalid': true});
      inToleranceRange = false;
    }

    if ((rpLong !== null)
    && ((rpLong.value > this.facilitySite.longitude + this.factolerance)
    || (rpLong.value < this.facilitySite.longitude - this.factolerance))) {
      this.toastr.error('', 'Release Point longitude must within the ' + this.factolerance +
      ' tolerance range of Facility longitude coordinate ' + this.facilitySite.longitude + '.', {positionClass: 'toast-top-right'});
      this.releasePointComponent.releasePointForm.controls['longitude'].setErrors({'invalid': true});
      inToleranceRange = false;
    }

    return inToleranceRange;
  }
}
