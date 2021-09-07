/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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

      result.sort((a, b) => (a.name > b.name) ? 1 : -1);
      result.forEach(prop => {
        if (prop.datatype !== 'boolean') {
          this.propertyForm.addControl(prop.name, new FormControl(prop.value, { validators: [
            Validators.required
          ]}));
        } else {
          const booleanValue = (prop.value.toLowerCase() === 'true');
          this.propertyForm.addControl(prop.name, new FormControl(booleanValue));
        }
      });

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
        this.toastr.success('', 'Properties updated successfully.');
      });

    }
  }

}
