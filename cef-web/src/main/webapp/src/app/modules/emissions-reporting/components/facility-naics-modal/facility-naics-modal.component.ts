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
import { Component, OnInit, Input } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, Validators, ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';
import { FacilityNaicsCode } from 'src/app/shared/models/facility-naics-code';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { LookupService } from 'src/app/core/services/lookup.service';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { legacyItemValidator } from 'src/app/modules/shared/directives/legacy-item-validator.directive';
import { NaicsCodeType } from 'src/app/shared/enums/naics-code-type';

@Component({
  selector: 'app-facility-naics-modal',
  templateUrl: './facility-naics-modal.component.html',
  styleUrls: ['./facility-naics-modal.component.scss']
})
export class FacilityNaicsModalComponent extends BaseSortableTable implements OnInit {
  @Input() facilitySiteId: number;
  @Input() facilityNaics: FacilityNaicsCode[];
  @Input() year: number;
  updateFacilityNaics: FacilityNaicsCode;
  selectedNaicsCode: FacilityNaicsCode;
  naicsCodeType = NaicsCodeType;
  selectedNaicsCodeType = NaicsCodeType.PRIMARY;
  primaryFlag = true;
  check = true;
  edit: boolean;

  naicsForm = this.fb.group({
    selectedNaics: [null, Validators.required],
    }, { validators: [this.checkValidNaics()]
  });

  facilityNaicsCode: FacilityNaicsCode[];

  constructor(private facilityService: FacilitySiteService,
              private lookupService: LookupService,
              private toastr: ToastrService,
              private fb: FormBuilder,
              public activeModal: NgbActiveModal) {
    super();
  }

  ngOnInit() {

    this.naicsForm.get('selectedNaics').setValidators([Validators.required, legacyItemValidator(this.year, 'NAICS Code', 'code')]);

    if (this.selectedNaicsCode) {
      this.naicsForm.get('selectedNaics').setValue(this.selectedNaicsCode);
    }

    this.lookupService.retrieveCurrentNaicsCodes(this.year)
    .subscribe(result => {
      this.facilityNaicsCode = result;
    });
  }

  isValid() {
    return this.naicsForm.valid;
  }

  onClose() {
    this.activeModal.dismiss();
  }

  onSubmit() {

    let tempFacilityNaicsCode = new FacilityNaicsCode();
    if (this.edit) {
      this.facilityNaics.forEach(facilityNaics => {

        if (this.naicsForm.value.selectedNaics && facilityNaics.code === this.naicsForm.value.selectedNaics.code) {
          const index = this.facilityNaics.indexOf(facilityNaics);
          tempFacilityNaicsCode = facilityNaics;
          this.facilityNaics.splice(index, 1);
        }
      });
    }

    this.facilityNaics.forEach(facilityNaics => {

      if (this.naicsForm.value.selectedNaics && facilityNaics.code === this.naicsForm.value.selectedNaics.code) {
        this.check = false;
        this.toastr.error('', 'This Facility already contains this NAICS code, duplicates are not allowed.');
      }

      if (this.naicsForm.value.selectedNaics && facilityNaics.naicsCodeType === this.naicsCodeType.PRIMARY && this.selectedNaicsCodeType === this.naicsCodeType.PRIMARY) {
        this.check = false;
        this.toastr.error('', 'Each facility must have only one NAICS code assigned as primary.');
      }

      if (facilityNaics.naicsCodeType === this.naicsCodeType.PRIMARY && this.selectedNaicsCodeType === this.naicsCodeType.PRIMARY && this.check) {
        this.updateFacilityNaics = facilityNaics;
      }

    });

    if (!this.check && this.edit) {
      this.facilityNaics.push(tempFacilityNaicsCode);
    }

    if (this.check) {

      if (!this.isValid()) {
        this.naicsForm.markAsTouched();
      } else {
        if (this.updateFacilityNaics) {

          this.updateFacilityNaics.naicsCodeType = this.naicsCodeType.SECONDARY;
          this.facilityService.updateFacilityNaics(this.updateFacilityNaics)
          .subscribe(() => {

          });
        }

        if (!this.edit) {
        const savedFacilityNaics = new FacilityNaicsCode();

          savedFacilityNaics.naicsCodeType = this.selectedNaicsCodeType;
        savedFacilityNaics.facilitySiteId = this.facilitySiteId;
        savedFacilityNaics.code = this.naicsForm.value.selectedNaics.code;
        savedFacilityNaics.description = this.naicsForm.value.selectedNaics.description;

        this.facilityService.createFacilityNaics(savedFacilityNaics)
        .subscribe(() => {
          this.activeModal.close();
        });
        } else {
          this.selectedNaicsCode.naicsCodeType = this.selectedNaicsCodeType;
          this.selectedNaicsCode.facilitySiteId = this.facilitySiteId;
          this.selectedNaicsCode.code = this.naicsForm.value.selectedNaics.code;
          this.selectedNaicsCode.description = this.naicsForm.value.selectedNaics.description;

          this.facilityService.updateFacilityNaics(this.selectedNaicsCode)
            .subscribe(() => {
              this.activeModal.close();
          });
          this.facilityNaics.push(this.selectedNaicsCode);
        }
      }
    }
    this.check = true;
  }

  checkValidNaics(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      if (this.naicsForm && this.naicsForm.value.selectedNaics === undefined) {
        return { invalidNaics: true };
      }
      return null;
    };
  }

  searchNAICS = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => this.facilityNaicsCode && this.facilityNaicsCode.filter(v => v.code.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || (v.description ? v.description.toLowerCase().indexOf(term.toLowerCase()) > -1 : false))
                                        .slice(0, 20))
    )

    naicsFormatter = (result: FacilityNaicsCode) => `${result.code}  -  ${result.description}`;
}
