import { Component, OnInit } from '@angular/core';
import { AdminPropertyService } from 'src/app/core/services/admin-property.service';
import { AppProperty } from 'src/app/shared/models/app-property';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, Validators, FormControl } from '@angular/forms';

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
      private toastr: ToastrService) { }

  ngOnInit() {
    this.propertyService.retrieveAll()
    .subscribe(result => {

      console.log(result);

      result.forEach(prop => {
        this.propertyForm.addControl(prop.name, new FormControl(prop.value, { validators: [
          Validators.required
        ]}));
      });

      console.log(this.propertyForm);

      this.properties = result;
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
