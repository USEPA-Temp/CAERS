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
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { User } from 'src/app/shared/models/user';
import { LookupService } from 'src/app/core/services/lookup.service';
import { UserContextService } from 'src/app/core/services/user-context.service';

@Component({
  selector: 'app-slt-properties',
  templateUrl: './slt-properties.component.html',
  styleUrls: ['./slt-properties.component.scss']
})
export class SltPropertiesComponent implements OnInit {

  properties: AppProperty[];
  user: User;
  agencyDataValues: BaseCodeLookup[];
  programSystemCode: BaseCodeLookup;
  slt: string;

  propertyForm = this.fb.group({});

  constructor(
      private userContextService: UserContextService,
	  private lookupService: LookupService,
      private propertyService: SltPropertyService,
      private fb: FormBuilder,
      private toastr: ToastrService) { }

  ngOnInit() {
    this.userContextService.getUser()
    .subscribe( user => {
          this.user = user;
      });

    this.lookupService.retrieveProgramSystemTypeCode()
    .subscribe(result => {
        this.agencyDataValues = result.sort((a, b) => (a.code > b.code) ? 1 : -1);
      });

    if (this.user.isReviewer) {
      this.slt = this.user.programSystemCode;
    }

    this.refreshSltPropertyList();

  }

  onAgencySelected() {
    this.slt = this.programSystemCode ? this.programSystemCode.code : null;
    this.refreshSltPropertyList();
  }


  refreshSltPropertyList() {

    if (this.slt !== null) {
      this.propertyService.retrieveAll(this.slt)
        .subscribe(result => {
          result.sort((a, b) => (a.name > b.name) ? 1 : -1);
          result.forEach(prop => {
			if (Object.keys(this.propertyForm.controls).length === 0) {
	            if (prop.datatype !== 'boolean') {
	              this.propertyForm.addControl(prop.name, new FormControl(prop.value, { validators: [
	                Validators.required
	              ]}));
	            } else {
	              const booleanValue = (prop.value.toLowerCase() === 'true');
	              this.propertyForm.addControl(prop.name, new FormControl(booleanValue));
	            }
			} else {
				if (prop.datatype !== 'boolean') {
				  this.propertyForm.setControl(prop.name, new FormControl(prop.value, { validators: [
	                Validators.required
	              ]}));
	            } else {
	              const booleanValue = (prop.value.toLowerCase() === 'true');
				  this.propertyForm.setControl(prop.name, new FormControl(booleanValue));
	            }
			}

          });
		  this.properties = result;
        });
      }
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

      this.propertyService.bulkUpdate(updatedProperties, this.slt)
      .subscribe(result => {
        this.toastr.success('', 'Properties updated successfully.');
      });

    }
  }

}
