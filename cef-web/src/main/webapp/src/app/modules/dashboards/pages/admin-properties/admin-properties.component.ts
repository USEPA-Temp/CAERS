import { Component, OnInit } from '@angular/core';
import { AdminPropertyService } from 'src/app/core/services/admin-property.service';
import { AppProperty } from 'src/app/shared/models/app-property';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-admin-properties',
  templateUrl: './admin-properties.component.html',
  styleUrls: ['./admin-properties.component.scss']
})
export class AdminPropertiesComponent implements OnInit {
  properties: AppProperty[];

  propertyForm = this.fb.group({});

  constructor(
      private propertyService: AdminPropertyService,
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