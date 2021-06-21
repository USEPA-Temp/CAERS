import { FacilitySite } from 'src/app/shared/models/facility-site';
import { Process } from 'src/app/shared/models/process';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { EmissionsProcessService } from 'src/app/core/services/emissions-process.service';
import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { ControlPath } from 'src/app/shared/models/control-path';
import { ControlPathService } from 'src/app/core/services/control-path.service';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { UserContextService } from 'src/app/core/services/user-context.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EditEmissionUnitInfoPanelComponent } from '../../components/edit-emission-unit-info-panel/edit-emission-unit-info-panel.component';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { EmissionUnitInfoComponent } from '../../components/emission-unit-info/emission-unit-info.component';


@Component({
  selector: 'app-emission-unit-dashboard',
  templateUrl: './emission-unit-dashboard.component.html',
  styleUrls: ['./emission-unit-dashboard.component.scss']
})
export class EmissionUnitDashboardComponent implements OnInit {
  emissionsUnit: EmissionUnit;
  processes: Process[];
  controlPaths: ControlPath[];
  facilitySiteId: number;

  readOnlyMode = true;

  @ViewChild(EmissionUnitInfoComponent)
  private infoComponentParent: EmissionUnitInfoComponent;

  constructor(
    private emissionUnitService: EmissionUnitService,
    private processService: EmissionsProcessService,
    private controlPathService: ControlPathService,
    private userContextService: UserContextService,
    private modalService: NgbModal,
    private route: ActivatedRoute,
    private sharedService: SharedService) { }

  ngOnInit() {
    this.route.paramMap
      .subscribe(map => {
        this.emissionUnitService.retrieve(+map.get('unitId'))
        .subscribe(unit => {
          this.emissionsUnit = unit;
          this.processService.retrieveForEmissionsUnit(this.emissionsUnit.id)
          .subscribe(processes => {
            this.processes = processes;
          });
        });

        this.controlPathService.retrieveForEmissionsUnit(+map.get('unitId'))
        .subscribe(controlPaths => {
          this.controlPaths = controlPaths;
        });

        // get the control paths again if change is emitted, in case a process has been deleted
        // and the contols paths have been removed
        this.sharedService.changeEmitted$
        .subscribe(() => {
          this.controlPathService.retrieveForEmissionsUnit(+map.get('unitId'))
          .subscribe(controlPaths => {
            this.controlPaths = controlPaths;
          });
        } );
    });
    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.facilitySiteId = data.facilitySite.id;
      this.userContextService.getUser().subscribe( user => {
        if (user.role !== 'Reviewer' && ReportStatus.IN_PROGRESS === data.facilitySite.emissionsReport.status) {
          this.readOnlyMode = false;
        }
      });

      this.sharedService.emitChange(data.facilitySite);
    });
  }

  canDeactivate(): Promise<boolean> | boolean {
    // Allow synchronous navigation (`true`) if both forms are clean
    if ((this.infoComponentParent.infoComponent === undefined || !this.infoComponentParent.infoComponent.emissionUnitForm.dirty)) {
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
    if ((this.infoComponentParent.infoComponent !== undefined && this.infoComponentParent.infoComponent.emissionUnitForm.dirty)) {
      event.preventDefault();
      event.returnValue = '';
    }
    return true;
  }
}
