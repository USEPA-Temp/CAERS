import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { FormBuilder, Validators, ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { LookupService } from 'src/app/core/services/lookup.service';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';
import { ToastrService } from 'ngx-toastr';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { ActivatedRoute } from '@angular/router';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { OperatingStatus } from 'src/app/shared/enums/operating-status';
import { VariableValidationType } from 'src/app/shared/enums/variable-validation-type';

@Component({
  selector: 'app-edit-emission-unit-info-panel',
  templateUrl: './edit-emission-unit-info-panel.component.html',
  styleUrls: ['./edit-emission-unit-info-panel.component.scss']
})
export class EditEmissionUnitInfoPanelComponent implements OnInit, OnChanges {
  @Input() emissionUnit: EmissionUnit;
  designCapacityWarning: any;
  facilitySite: FacilitySite;
  emissionUnitIdentifiers: string[] = [];
  facilityOpCode: BaseCodeLookup;
  facilitySourceTypeCode: BaseCodeLookup;
  edit = true;

  emissionUnitForm = this.fb.group({
    unitTypeCode: [null, Validators.required],
    operatingStatusCode: [null, Validators.required],
    unitOfMeasureCode: [null],
    unitIdentifier: ['', [
      Validators.required,
      Validators.maxLength(20)
    ]],
    statusYear: ['', [
      Validators.min(1900),
      Validators.max(2050),
      Validators.pattern('[0-9]*')
    ]],
    designCapacity: ['', [
      Validators.min(0.01),
      Validators.max(100000000),
      Validators.pattern('^[0-9]*\\.?[0-9]+$'),
      Validators.maxLength(20)
    ]],
    description: ['', [
      this.requiredIfOperating(),
      Validators.maxLength(100)
    ]],
    comments: [null, Validators.maxLength(400)]
  }, { validators: [
    this.unitTypeCheck(),
    this.emissionUnitIdentifierCheck(),
    this.facilitySiteStatusCheck(),
    this.capacityUomCheck(),
    this.capacityLegacyUomCheck(),
    this.statusYearRequiredCheck()]
  });

  subFacilityOperatingStatusValues: BaseCodeLookup[];
  unitTypeValues: BaseCodeLookup[];
  uomValues: UnitMeasureCode[];
  designCapacityUomValues: UnitMeasureCode[];

  // Unit type codes that should have a design capacity
  typeCodeDesignCapacity = ['100', '120', '140', '160', '180'];

  constructor(private fb: FormBuilder,
              private lookupService: LookupService,
              public formUtils: FormUtilsService,
              private toastr: ToastrService,
              private emissionUnitService: EmissionUnitService,
              private route: ActivatedRoute
              ) { }

  ngOnInit() {

    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.facilitySourceTypeCode = data.facilitySite.facilitySourceTypeCode;
      this.facilityOpCode = data.facilitySite.operatingStatusCode;
      this.emissionUnitService.retrieveForFacility(data.facilitySite.id)
      .subscribe(emissionUnits => {
        emissionUnits.forEach(eu => {
          this.emissionUnitIdentifiers.push(eu.unitIdentifier);
        });

        // if an emission unit is being edited then filter that identifer out the list so the validator
        // check doesnt identify it as a duplicate
        if (this.emissionUnit) {
          this.emissionUnitIdentifiers = this.emissionUnitIdentifiers.filter(identifer =>
            identifer.toString() !== this.emissionUnit.unitIdentifier);
        } else {
          this.edit = false;
        }

      });
    });

    this.lookupService.retrieveUom()
    .subscribe(result => {
      this.uomValues = result;
      this.designCapacityUomValues = this.uomValues.filter(val => val.unitDesignCapacity);
    });

    this.lookupService.retrieveSubFacilityOperatingStatus()
    .subscribe(result => {
      this.subFacilityOperatingStatusValues = result;
    });

    this.lookupService.retrieveUnitType()
    .subscribe(result => {
        result.sort((a, b) => {
          if (a.description < b.description) {
            return -1;
          }
          if (a.description > b.description) {
            return 1;
          }
          return 0;
        });
        this.unitTypeValues = result;
    });
  }

  ngOnChanges() {
    this.emissionUnitForm.reset(this.emissionUnit);
  }

  onChange(newValue) {
    if (newValue && this.edit) {
      this.emissionUnitForm.controls.statusYear.reset();
      this.toastr.warning('', 'If the operating status of the Emission Unit is changed,' +
       ' then the operating status of all the child Emission Processes that are underneath ' +
       ' this unit will also be updated, unless they are already Permanently Shutdown.');
    }
    this.emissionUnitForm.controls.statusYear.markAsTouched();
    this.emissionUnitForm.controls.description.updateValueAndValidity();
  }

  // Design capacity should be entered if type code is 100, 120, 140, 160, or 180.
  unitTypeCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const type = control.get('unitTypeCode');
      const designCapacity = control.get('designCapacity');

      if ((designCapacity.value === null || designCapacity.value === '') && type.value !== null) {
        for (const item of this.typeCodeDesignCapacity) {
          if (type.value.code === item) {
            this.designCapacityWarning = { invalidDesignCapacity: {designCapacity} };
            break;
          } else {
            this.designCapacityWarning = null;
          }
        }
      } else {
        this.designCapacityWarning = null;
      }
      return null;
    };
  }

  emissionUnitIdentifierCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      if (this.emissionUnitIdentifiers) {
        if (control.get('unitIdentifier').value.trim() === '') {
          control.get('unitIdentifier').setErrors({required: true});
        } else if (this.emissionUnitIdentifiers.includes(control.get('unitIdentifier').value.trim())) {
          return { duplicateEmissionUnitIdentifier: true };
        }
      }
      return null;
    };
  }

  facilitySiteStatusCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const controlStatus = control.get('operatingStatusCode').value;

      if (this.facilityOpCode && controlStatus
        && (this.facilitySourceTypeCode === null || (this.facilitySourceTypeCode.code !== VariableValidationType.LANDFILL_SOURCE_TYPE))) {

        if (this.facilityOpCode.code === OperatingStatus.TEMP_SHUTDOWN
          && controlStatus.code !== OperatingStatus.PERM_SHUTDOWN
          && controlStatus.code !== OperatingStatus.TEMP_SHUTDOWN) {
          return {invalidStatusCodeTS: true};

        } else if (this.facilityOpCode.code === OperatingStatus.PERM_SHUTDOWN
          && controlStatus.code !== OperatingStatus.PERM_SHUTDOWN) {
          return {invalidStatusCodePS: true};

        }
      }
      return null;
    };
  }

  capacityUomCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const designCapacity = control.get('designCapacity').value;
      const designCapacityUom = control.get('unitOfMeasureCode').value;

      if ((designCapacity && !designCapacityUom) || (!designCapacity && designCapacityUom)) {
        return {invalidUom: true};
      }
      return null;
    };
  }

  capacityLegacyUomCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const designCapacityUom = control.get('unitOfMeasureCode').value;

      if (control.get('operatingStatusCode').value
        && control.get('operatingStatusCode').value.code.includes(OperatingStatus.OPERATING)) {
        if (designCapacityUom && (designCapacityUom.legacy || !designCapacityUom.unitDesignCapacity)) {
          return {eisUomInvalid: true};
        }
      }
      return null;
    };
  }

  statusYearRequiredCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const statusYear = control.get('statusYear').value;

      if (statusYear === null || statusYear === '') {
        control.get('statusYear').setErrors({statusYearRequiredFailed: true});
      }
      return null;
    };
  }

  requiredIfOperating() {
    return (formControl => {
      if (!formControl.parent) {
        return null;
      }

      if (this.emissionUnitForm.get('operatingStatusCode').value
        && this.emissionUnitForm.get('operatingStatusCode').value.code.includes(OperatingStatus.OPERATING)) {
        return Validators.required(formControl);
      }
      return null;
    });
  }
}
