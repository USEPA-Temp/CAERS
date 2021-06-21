import {FacilitySite} from 'src/app/shared/models/facility-site';
import {Process} from 'src/app/shared/models/process';
import {ReleasePoint} from 'src/app/shared/models/release-point';
import {EmissionsProcessService} from 'src/app/core/services/emissions-process.service';
import {ReleasePointService} from 'src/app/core/services/release-point.service';
import {Component, OnInit, ViewChild, Input, HostListener} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {SharedService} from 'src/app/core/services/shared.service';
import {ControlPath} from 'src/app/shared/models/control-path';
import {ControlPathService} from 'src/app/core/services/control-path.service';
import {ReportStatus} from 'src/app/shared/enums/report-status';
import {EditReleasePointPanelComponent} from '../../components/edit-release-point-panel/edit-release-point-panel.component';
import {UserContextService} from 'src/app/core/services/user-context.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
    selector: 'app-release-point-details',
    templateUrl: './release-point-details.component.html',
    styleUrls: ['./release-point-details.component.scss']
})
export class ReleasePointDetailsComponent implements OnInit {
    @Input() releasePoint: ReleasePoint;
    processes: Process[];
    controlPaths: ControlPath[];
    parentComponentType = 'releasePointAppt';
    facilitySite: FacilitySite;

    fugitive = false;
    readOnlyMode = true;
    editInfo = false;

    @ViewChild(EditReleasePointPanelComponent)
    private releasePointComponent: EditReleasePointPanelComponent;

    constructor(
        private releasePointService: ReleasePointService,
        private processService: EmissionsProcessService,
        private controlPathService: ControlPathService,
        private userContextService: UserContextService,
        private route: ActivatedRoute,
        private modalService: NgbModal,
        private sharedService: SharedService) {
    }

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
                this.userContextService.getUser().subscribe(user => {
                    if (user.role !== 'Reviewer' && ReportStatus.IN_PROGRESS === data.facilitySite.emissionsReport.status) {
                        this.readOnlyMode = false;
                    }
                });
                this.facilitySite = data.facilitySite;
                this.sharedService.emitChange(data.facilitySite);
            });

    }

    setEditInfo(value: boolean) {
        this.editInfo = value;
    }

    releasePointType = (): string => {
        return this.fugitive ? 'Fugitive' : 'Stack'
    };

    isDiameter = (): boolean => {
        return !!this.releasePoint.stackDiameter;
    }

    updateReleasePoint() {
        if (!this.releasePointComponent.releasePointForm.valid) {
            this.releasePointComponent.releasePointForm.markAllAsTouched();
        } else {
            const updatedReleasePoint = new ReleasePoint();

            Object.assign(updatedReleasePoint, this.releasePointComponent.releasePointForm.value);
            updatedReleasePoint.id = this.releasePoint.id;

            if (this.releasePointComponent.releasePointForm.get('exitGasFlowRate').value === null
                || this.releasePointComponent.releasePointForm.get('exitGasFlowRate').value === '') {
                updatedReleasePoint.exitGasFlowUomCode = null;
            }
            if (this.releasePointComponent.releasePointForm.get('exitGasVelocity').value === null
                || this.releasePointComponent.releasePointForm.get('exitGasVelocity').value === '') {
                updatedReleasePoint.exitGasVelocityUomCode = null;
            }
            if (this.releasePointComponent.releasePointForm.get('fenceLineDistance').value === null
                || this.releasePointComponent.releasePointForm.get('fenceLineDistance').value === '') {
                updatedReleasePoint.fenceLineUomCode = null;
            }
            if (this.releasePointComponent.releasePointForm.get('fugitiveWidth').value === null
                || this.releasePointComponent.releasePointForm.get('fugitiveWidth').value === '') {
                updatedReleasePoint.fugitiveWidthUomCode = null;
            }
            if (this.releasePointComponent.releasePointForm.get('fugitiveLength').value === null
                || this.releasePointComponent.releasePointForm.get('fugitiveLength').value === '') {
                updatedReleasePoint.fugitiveLengthUomCode = null;
            }
            if (this.releasePointComponent.releasePointForm.get('stackWidth').value === null
                || this.releasePointComponent.releasePointForm.get('stackWidth').value === '') {
                updatedReleasePoint.stackWidthUomCode = null;
            }
            if (this.releasePointComponent.releasePointForm.get('stackLength').value === null
                || this.releasePointComponent.releasePointForm.get('stackLength').value === '') {
                updatedReleasePoint.stackLengthUomCode = null;
            }
            if (this.releasePointComponent.releasePointForm.get('fugitiveHeight').value === null
                || this.releasePointComponent.releasePointForm.get('fugitiveHeight').value === '') {
                updatedReleasePoint.fugitiveHeightUomCode = null;
            }

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
    }

    canDeactivate(): Promise<boolean> | boolean {
    // Allow synchronous navigation (`true`) if both forms are clean
    if (!this.editInfo || (this.releasePointComponent !== undefined && !this.releasePointComponent.releasePointForm.dirty)) {
        return true;
    }
    // Otherwise ask the user with the dialog service and return its
    // promise which resolves to true or false when the user decides
    const modalMessage = 'There are unsaved edits on the screen. Leaving without saving will discard any changes. Are you sure you want to continue?';
    const modalRef = this.modalService.open(ConfirmationDialogComponent);
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.title = 'Unsaved Changes';
    modalRef.componentInstance.confirmButtonText = 'Confirm';
    return modalRef.result;
  }

  @HostListener('window:beforeunload', ['$event'])
  beforeunloadHandler(event) {
    if (this.editInfo && this.releasePointComponent.releasePointForm.dirty) {
      event.preventDefault();
      event.returnValue = '';
    }
    return true;
  }
  
}
