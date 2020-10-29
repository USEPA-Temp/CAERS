import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormBuilder, Validators, FormGroup, ValidatorFn, ValidationErrors } from '@angular/forms';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FipsStateCode } from 'src/app/shared/models/fips-state-code';
import { numberValidator } from 'src/app/modules/shared/directives/number-validator.directive';
import { FacilityCategoryCode } from 'src/app/shared/models/facility-category-code';
import { FipsCounty } from 'src/app/shared/models/fips-county';
import { ToastrService } from 'ngx-toastr';
import { legacyItemValidator } from 'src/app/modules/shared/directives/legacy-item-validator.directive';
import { InventoryYearCodeLookup } from 'src/app/shared/models/inventory-year-code-lookup';

@Component({
  selector: 'app-edit-facility-site-info-panel',
  templateUrl: './edit-facility-site-info-panel.component.html',
  styleUrls: ['./edit-facility-site-info-panel.component.scss']
})
export class EditFacilitySiteInfoPanelComponent implements OnInit, OnChanges {
  @Input() facilitySite: FacilitySite;

  facilitySiteForm = this.fb.group({
    altSiteIdentifier: [''],
    name: ['', [
      Validators.maxLength(80),
      Validators.required]],
    latitude: ['', [
      Validators.required,
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-90),
      Validators.max(90),
    ]],
    longitude: ['', [
      Validators.required,
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-180),
      Validators.max(180),
    ]],
    operatingStatusCode: [null, Validators.required],
    facilityCategoryCode: [null],
    facilitySourceTypeCode: [null],
    statusYear: ['', [
      Validators.required,
      Validators.min(1900),
      Validators.max(2050),
      numberValidator()]],
    tribalCode: [null],
    streetAddress: ['', [
      Validators.maxLength(100),
      Validators.required]],
    city: ['', [
      Validators.required,
      Validators.maxLength(60)]],
    stateCode: [null, Validators.required],
    postalCode: ['', [
      Validators.required,
      Validators.pattern('^\\d{5}(-\\d{4})?$')]],
    mailingStreetAddress: [''],
    mailingCity: [''],
    mailingStateCode: [null],
    mailingPostalCode: ['', Validators.pattern('^\\d{5}(-\\d{4})?$')],
    countyCode: [null, Validators.required],
    description: ['', Validators.maxLength(100)],
    comments: ['', Validators.maxLength(400)]
  }, {validators: this.mailingAddressValidator()});

  facilityOperatingStatusValues: BaseCodeLookup[];
  tribalCodeValues: BaseCodeLookup[];
  fipsStateCode: FipsStateCode[];
  facilityCategoryCodeValues: FacilityCategoryCode[];
  facilitySourceTypeValues: InventoryYearCodeLookup[];
  counties: FipsCounty[];

  constructor(
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    private fb: FormBuilder,
    private toastr: ToastrService) { }

  ngOnInit() {
    this.facilitySiteForm.get('countyCode').setValidators([Validators.required, legacyItemValidator(this.facilitySite.emissionsReport.year, 'County', 'name')]);

    this.facilitySiteForm.get('stateCode').valueChanges
    .subscribe(value => {
      if (value) {
        if (this.facilitySiteForm.value.countyCode && value.code
            && this.facilitySiteForm.value.countyCode.fipsStateCode.code !== value.code) {
          this.facilitySiteForm.get('countyCode').setValue(null);
        }
        this.lookupService.retrieveCurrentFipsCountiesForState(value.code, this.facilitySite.emissionsReport.year)
        .subscribe(result => {
          this.counties = result;
        });
      } else {
        this.counties = [];
        this.facilitySiteForm.get('countyCode').setValue(null);
      }
    });

    this.lookupService.retrieveFacilityOperatingStatus()
    .subscribe(result => {
      this.facilityOperatingStatusValues = result;
    });

    this.lookupService.retrieveTribalCode()
    .subscribe(result => {
      this.tribalCodeValues = result;
    });

    this.lookupService.retrieveFacilityCategory()
    .subscribe(result => {
      this.facilityCategoryCodeValues = result;
    });

    this.facilitySiteForm.get('facilitySourceTypeCode').setValidators([legacyItemValidator(this.facilitySite.emissionsReport.year, 'Facility Source Type Code', 'description')]);

    this.lookupService.retrieveCurrentFacilitySourceType(this.facilitySite.emissionsReport.year)
    .subscribe(result => {
      this.facilitySourceTypeValues = result;
    });

    this.lookupService.retrieveFipsStateCode()
    .subscribe(result => {
      this.fipsStateCode = result;

      // required to trigger a fetch of counties
      this.facilitySiteForm.get('stateCode').updateValueAndValidity();
    });
  }


  ngOnChanges() {
    this.facilitySiteForm.reset(this.facilitySite);
  }

  onChange(newValue) {
    if(newValue) {
      this.facilitySiteForm.controls.statusYear.reset();
      this.toastr.warning('', 'If the operating status of the Facility Site is changed, then the operating status of all the child Emission Units, Processes, Controls, and Release Points that are underneath this Facility Site will also be updated, unless they are already Permanently Shutdown.');
    }
  }

  mailingAddressValidator(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const addressControl = control.get('mailingStreetAddress');
      const uspsControl = control.get('mailingStateCode');
      const cityControl = control.get('mailingCity');
      const postalCodeControl = control.get('mailingPostalCode');

      if (addressControl.enabled) {
        if (uspsControl.value === null || cityControl.value === '' || postalCodeControl.value === '') {
          return addressControl.value === '' ? null : {mailingStreetAddress : {value: this.facilitySite.mailingStreetAddress}};
      }}
      return null;
    };
  }

}
