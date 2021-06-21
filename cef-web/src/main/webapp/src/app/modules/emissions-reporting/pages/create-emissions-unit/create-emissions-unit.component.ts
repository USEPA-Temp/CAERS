import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import { EditEmissionUnitInfoPanelComponent } from 'src/app/modules/emissions-reporting/components/edit-emission-unit-info-panel/edit-emission-unit-info-panel.component';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';


@Component({
  selector: 'app-create-emissions-unit',
  templateUrl: './create-emissions-unit.component.html',
  styleUrls: ['./create-emissions-unit.component.scss']
})
export class CreateEmissionsUnitComponent implements OnInit {


  @ViewChild(EditEmissionUnitInfoPanelComponent, { static: true })
  private infoComponent: EditEmissionUnitInfoPanelComponent;

  facilitySiteId: number;
  reportId: number;
  editInfo = true;

  constructor(private emissionUnitService: EmissionUnitService,
              private router: Router,
              private route: ActivatedRoute,
              private sharedService: SharedService,
              private modalService: NgbModal,
              private facilitySiteService: FacilitySiteService) { }

  ngOnInit() {
      this.route.paramMap
      .subscribe(map => {
        this.reportId = parseInt(map.get('reportId'));
      });

      this.facilitySiteService.retrieveForReport(this.reportId)
      .subscribe(result => {
        this.facilitySiteId = result.id;
      });

      this.route.data
      .subscribe(data => {
        this.sharedService.emitChange(data.facilitySite);
      });
  }

  isValid() {
    return this.infoComponent.emissionUnitForm.valid;
  }

  onSubmit() {
        if (!this.isValid()) {
          this.infoComponent.emissionUnitForm.markAllAsTouched();
        }
        else {
          const emissionUnit = new EmissionUnit();

          Object.assign(emissionUnit, this.infoComponent.emissionUnitForm.value);
          emissionUnit.facilitySiteId = this.facilitySiteId;
          emissionUnit.unitIdentifier = this.infoComponent.emissionUnitForm.controls.unitIdentifier.value.trim();

          this.emissionUnitService.create(emissionUnit)
          .subscribe(() => {
            this.editInfo = false;
            this.sharedService.updateReportStatusAndEmit(this.route);
            this.router.navigate(['../..'], { relativeTo: this.route });
          });
        }
  }

  onCancel() {
    this.editInfo = false;
    this.router.navigate(['../..'], { relativeTo: this.route });
  }

  canDeactivate(): Promise<boolean> | boolean {
    // Allow synchronous navigation (`true`) if both forms are clean
    if (!this.editInfo || !this.infoComponent.emissionUnitForm.dirty) {
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
    if (this.editInfo && this.infoComponent.emissionUnitForm.dirty) {
      event.preventDefault();
      event.returnValue = '';
    }
    return true;
  }
  
}
