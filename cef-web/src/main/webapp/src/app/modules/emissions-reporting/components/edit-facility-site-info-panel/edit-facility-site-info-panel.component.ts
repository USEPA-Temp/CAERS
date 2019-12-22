import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormBuilder, Validators, FormGroup, ValidatorFn, ValidationErrors } from '@angular/forms';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FipsStateCode } from 'src/app/shared/models/fips-state-code';
import { numberValidator } from 'src/app/modules/shared/directives/number-validator.directive';

@Component({
  selector: 'app-edit-facility-site-info-panel',
  templateUrl: './edit-facility-site-info-panel.component.html',
  styleUrls: ['./edit-facility-site-info-panel.component.scss']
})
export class EditFacilitySiteInfoPanelComponent implements OnInit, OnChanges {
  @Input() facilitySite: FacilitySite;

  facilitySiteForm = this.fb.group({
    eisProgramId: [''],
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
    statusYear: ['', [
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
    postalCode: ['', Validators.pattern('^[0-9]{5}([\-]?[0-9]{4})?$')],
    mailingStreetAddress: [''],
    mailingCity: [''],
    mailingStateCode: [null],
    mailingPostalCode: ['', Validators.pattern('^[0-9]{5}([\-]?[0-9]{4})?$')],
    county: ['', [
      Validators.maxLength(43)
    ]],
  }, {validators: this.mailingAddressValidator()});

  operatingStatusValues: BaseCodeLookup[];
  tribalCodeValues: BaseCodeLookup[];
  fipsStateCode: FipsStateCode[];

  constructor(
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    private fb: FormBuilder) { }

  ngOnInit() {

    this.lookupService.retrieveOperatingStatus()
    .subscribe(result => {
      this.operatingStatusValues = result;
    });

    this.lookupService.retrieveTribalCode()
    .subscribe(result => {
      this.tribalCodeValues = result;
    });

    this.lookupService.retrieveFipsStateCode()
    .subscribe(result => {
      this.fipsStateCode = result;
      result.forEach(item => {

        if (this.facilitySite.mailingStateCode && item.uspsCode == this.facilitySite.mailingStateCode) {
          this.facilitySiteForm.controls['mailingStateCode'].setValue(item);
        }

        if (this.facilitySite.stateCode && item.uspsCode == this.facilitySite.stateCode) {
          this.facilitySiteForm.controls['stateCode'].setValue(item);
        }
      });
    });
  }


  ngOnChanges() {
    this.facilitySiteForm.reset(this.facilitySite);
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
