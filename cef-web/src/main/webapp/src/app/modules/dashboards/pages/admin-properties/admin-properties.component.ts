import { Component, OnInit } from '@angular/core';
import { AdminPropertyService } from 'src/app/core/services/admin-property.service';
import { AppProperty } from 'src/app/shared/models/app-property';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { RecalculateEmissionTonsModalComponent } from 'src/app/modules/dashboards/components/recalculate-emission-tons-modal/recalculate-emission-tons-modal.component';
import { UserFacilityAssociationService } from 'src/app/core/services/user-facility-association.service';

@Component({
  selector: 'app-admin-properties',
  templateUrl: './admin-properties.component.html',
  styleUrls: ['./admin-properties.component.scss']
})
export class AdminPropertiesComponent implements OnInit {
  properties: AppProperty[];

  propertyForm = this.fb.group({});

  migrating = false;

  constructor(
      private propertyService: AdminPropertyService,
      private ufaService: UserFacilityAssociationService,
      private fb: FormBuilder,
      private modalService: NgbModal,
      private toastr: ToastrService) { }

  ngOnInit() {
    this.propertyService.retrieveAll()
    .subscribe(result => {

      console.log(result);

      result.sort((a, b) => (a.name > b.name) ? 1 : -1);
      result.forEach(prop => {
        this.propertyForm.addControl(prop.name, new FormControl(prop.value, { validators: [
          Validators.required
        ]}));
      });

      console.log(this.propertyForm);

      this.properties = result;
    });
  }

  openTestEmailModal() {

    const adminEmails = this.properties.find(p => p.name.toLowerCase() === 'email.admin').value;

    const modalMessage = `Are you sure you want to send a test email to ${adminEmails}?`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent);
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
        this.sendTestEmail();
    });
  }

  sendTestEmail() {
    this.propertyService.sendTestEmail()
    .subscribe(() => {
      this.toastr.success('', 'Test email sent');
    });
  }

  openRecalculateEmissionTonsModal() {

    const modalRef = this.modalService.open(RecalculateEmissionTonsModalComponent);
    modalRef.result.then((reportId: number) => {
      if (reportId) {
        this.recalculateEmissionTons(reportId);
      }
    });
  }

  recalculateEmissionTons(reportId: number) {
    this.propertyService.recalculateEmissionTotalTons(reportId)
    .subscribe(result => {
      console.log(result);
      this.toastr.success('', result.length + ' emissions had their emission total in tons updated.');
    });
  }

  openMigrateUserAssociationsModal() {

    const modalMessage = `Are you sure you want to migrate user associations? This should only ever be done once.`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent);
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
        this.migrateUserAssociations();
    });
  }

  migrateUserAssociations() {
    this.migrating = true;
    this.ufaService.migrateUserAssociations()
    .subscribe(() => {
      this.toastr.success('', 'User Associations Migrated');
      this.migrating = false;
    });
  }

  onSubmit() {
    if (!this.propertyForm.valid) {
      this.propertyForm.markAllAsTouched();
    } else {

      const updatedProperties: AppProperty[] = [];
      this.properties.forEach(prop => {
        if (prop.value !== this.propertyForm.get([prop.name]).value) {
          prop.value = this.propertyForm.get([prop.name]).value;
          updatedProperties.push(prop);
        }
      });

      this.propertyService.bulkUpdate(updatedProperties)
      .subscribe(result => {
        console.log(result);
        this.toastr.success('', 'Properties updated successfully.');
      });

    }
  }

}
