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
import { legacyUomValidator } from 'src/app/modules/shared/directives/legacy-uom-validator.directive';

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
  edit = true;

  emissionUnitForm = this.fb.group({
    unitTypeCode: [null, Validators.required],
    operatingStatusCode: [null, Validators.required],
    unitOfMeasureCode: [null, legacyUomValidator()],
    unitIdentifier: ['', [
      Validators.required,
      Validators.maxLength(20)
    ]],
    statusYear: ['', [
      Validators.required,
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
      Validators.required,
      Validators.maxLength(100)
    ]],
    comments: ['', Validators.maxLength(400)]
  }, { validators: [
    this.unitTypeCheck(),
    this.emissionUnitIdentifierCheck(),
    this.facilitySiteStatusCheck()]
  });

  operatingStatusValues: BaseCodeLookup[];
  unitTypeValues: BaseCodeLookup[];
  uomValues: UnitMeasureCode[];

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
      this.facilityOpCode = data.facilitySite.operatingStatusCode;
      this.emissionUnitService.retrieveForFacility(data.facilitySite.id)
      .subscribe(emissionUnits => {
        emissionUnits.forEach(eu => {
          this.emissionUnitIdentifiers.push(eu.unitIdentifier);
        });

        // if an emission unit is being edited then filter that identifer out the list so the validator check doesnt identify it as a duplicate
        if (this.emissionUnit) {
          this.emissionUnitIdentifiers = this.emissionUnitIdentifiers.filter(identifer => identifer.toString() !== this.emissionUnit.unitIdentifier);
        } else {
          this.edit = false;
        }

      });
    });

    this.lookupService.retrieveUom()
    .subscribe(result => {
      this.uomValues = result;
    });

    this.lookupService.retrieveOperatingStatus()
    .subscribe(result => {
      this.operatingStatusValues = result;
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
    this.uomCapacityCheck();
  }

  ngOnChanges() {
    this.emissionUnitForm.reset(this.emissionUnit);
  }

  onChange(newValue) {
    if (newValue && this.edit) {
      this.emissionUnitForm.controls.statusYear.reset();
      this.toastr.warning('', 'If the operating status of the Emission Unit is changed, then the operating status of all the child Emission Processes that are underneath this unit will also be updated, unless they are already Permanently Shutdown.');
    }
  }

  uomCapacityCheck() {
    if (this.emissionUnitForm.controls.designCapacity.value !== null && this.emissionUnitForm.controls.designCapacity.value !== '') {
      this.emissionUnitForm.controls.unitOfMeasureCode.setValidators([Validators.required, legacyUomValidator()]);
      this.emissionUnitForm.controls.designCapacity.updateValueAndValidity();
      this.emissionUnitForm.controls.unitOfMeasureCode.updateValueAndValidity();
    } else {
      this.emissionUnitForm.controls.unitOfMeasureCode.setValidators([legacyUomValidator()]);
      this.emissionUnitForm.controls.unitOfMeasureCode.reset();
    }
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
      const statusPermShutdown = 'PS';
      const statusTempShutdown = 'TS';
      const controlStatus = control.get('operatingStatusCode').value;

      if (this.facilityOpCode && controlStatus) {
        if (this.facilityOpCode.code === statusTempShutdown
          && controlStatus.code !== statusPermShutdown
          && controlStatus.code !== statusTempShutdown) {
            return {invalidStatusCodeTS: true};
          } else if (this.facilityOpCode.code === statusPermShutdown
          && controlStatus.code !== statusPermShutdown) {
            return {invalidStatusCodePS: true};
          }
      }
      return null;
    };
  }
}
