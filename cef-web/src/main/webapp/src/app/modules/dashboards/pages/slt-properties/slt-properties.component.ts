import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormControl } from '@angular/forms';
import { SltPropertyService } from 'src/app/core/services/slt-property.service';
import { ToastrService } from 'ngx-toastr';
import { AppProperty } from 'src/app/shared/models/app-property';

@Component({
  selector: 'app-slt-properties',
  templateUrl: './slt-properties.component.html',
  styleUrls: ['./slt-properties.component.scss']
})
export class SltPropertiesComponent implements OnInit {

  properties: AppProperty[];

  propertyForm = this.fb.group({});

  constructor(
      private propertyService: SltPropertyService,
      private fb: FormBuilder,
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
