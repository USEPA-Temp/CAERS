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
  primaryFlag = false;
  check = true;

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

    this.lookupService.retrieveCurrentNaicsCodes(this.year)
    .subscribe(result => {
      this.facilityNaicsCode = result;
    });

  }

  setPrimary() {
    this.primaryFlag = !this.primaryFlag;
  }

  isValid() {
    return this.naicsForm.valid;
  }

  onClose() {
    this.activeModal.dismiss();
  }

  onSubmit() {

    this.facilityNaics.forEach(facilityNaics => {

      if (this.naicsForm.value.selectedNaics && facilityNaics.code === this.naicsForm.value.selectedNaics.code) {
        this.check = false;
        this.toastr.error('', 'This Facility already contains this NAICS code, duplicates are not allowed.');
      }

      if (facilityNaics.primaryFlag && this.primaryFlag && this.check) {
        this.updateFacilityNaics = facilityNaics;
      }

    });

    if (this.check) {

      if (!this.isValid()) {
        this.naicsForm.markAsTouched();
      } else {
        if (this.updateFacilityNaics) {

          this.updateFacilityNaics.primaryFlag = false;
          this.facilityService.updateFacilityNaics(this.updateFacilityNaics)
          .subscribe(() => {

          });
        }

        const savedFacilityNaics = new FacilityNaicsCode();

        savedFacilityNaics.primaryFlag = this.primaryFlag;
        savedFacilityNaics.facilitySiteId = this.facilitySiteId;
        savedFacilityNaics.code = this.naicsForm.value.selectedNaics.code;
        savedFacilityNaics.description = this.naicsForm.value.selectedNaics.description;

        this.facilityService.createFacilityNaics(savedFacilityNaics)
        .subscribe(() => {
          this.activeModal.close();
        });
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
