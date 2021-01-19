import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FipsStateCode } from 'src/app/shared/models/fips-state-code';
import { FacilityCategoryCode } from 'src/app/shared/models/facility-category-code';
import { FipsCounty } from 'src/app/shared/models/fips-county';
import { ToastrService } from 'ngx-toastr';
import { InventoryYearCodeLookup } from 'src/app/shared/models/inventory-year-code-lookup';
import { LookupService } from 'src/app/core/services/lookup.service';
import { numberValidator } from 'src/app/modules/shared/directives/number-validator.directive';


@Component({
  selector: 'app-edit-master-facility-info',
  templateUrl: './edit-master-facility-info.component.html',
  styleUrls: ['./edit-master-facility-info.component.scss']
})
export class EditMasterFacilityInfoComponent implements OnInit, OnChanges {
  @Input() facility: MasterFacilityRecord;

  facilityOperatingStatusValues: BaseCodeLookup[];
  tribalCodeValues: BaseCodeLookup[];
  fipsStateCode: FipsStateCode[];
  facilityCategoryCodeValues: FacilityCategoryCode[];
  facilitySourceTypeValues: InventoryYearCodeLookup[];
  counties: FipsCounty[];

  facilitySiteForm = this.fb.group({
    agencyFacilityId: '',
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
    description: ['', Validators.maxLength(100)]
  }, {validators: this.mailingAddressValidator()});

  constructor(
    public formUtils: FormUtilsService,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private lookupService: LookupService) { }

  ngOnInit(): void {

    this.facilitySiteForm.get('countyCode').setValidators([Validators.required]);

    this.facilitySiteForm.get('stateCode').valueChanges
    .subscribe(value => {
      if (value) {
        if (this.facilitySiteForm.value.countyCode && value.code
            && this.facilitySiteForm.value.countyCode.fipsStateCode.code !== value.code) {
          this.facilitySiteForm.get('countyCode').setValue(null);
        }
        this.lookupService.retrieveCurrentFipsCountiesForState(value.code, (new Date()).getFullYear())
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

    this.lookupService.retrieveCurrentFacilitySourceType((new Date()).getFullYear())
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
    this.facilitySiteForm.reset(this.facility);
  }


  mailingAddressValidator(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const addressControl = control.get('mailingStreetAddress');
      const uspsControl = control.get('mailingStateCode');
      const cityControl = control.get('mailingCity');
      const postalCodeControl = control.get('mailingPostalCode');

      if (addressControl.enabled) {
        if (uspsControl.value === null || cityControl.value === '' || postalCodeControl.value === '') {
          return addressControl.value === '' ? null : {mailingStreetAddress : {value: this.facility.mailingStreetAddress}};
      }}
      return null;
    };
  }

}