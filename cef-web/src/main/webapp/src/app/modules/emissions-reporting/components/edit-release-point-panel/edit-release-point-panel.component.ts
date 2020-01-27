import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormBuilder, Validators, ValidationErrors, ValidatorFn, FormGroup } from '@angular/forms';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';
import { numberValidator } from 'src/app/modules/shared/directives/number-validator.directive';
import { wholeNumberValidator } from 'src/app/modules/shared/directives/whole-number-validator.directive';

@Component({
  selector: 'app-edit-release-point-panel',
  templateUrl: './edit-release-point-panel.component.html',
  styleUrls: ['./edit-release-point-panel.component.scss']
})
export class EditReleasePointPanelComponent implements OnInit, OnChanges {
  @Input() releasePoint: ReleasePoint;
  releaseType: string;

  releasePointForm = this.fb.group({
    releasePointIdentifier: ['', [
      Validators.required,
      Validators.maxLength(20)
    ]],
    typeCode: [null, Validators.required],
    description: ['', [
      Validators.required,
      Validators.maxLength(200),
    ]],
    operatingStatusCode: [null, Validators.required],
    statusYear: ['', [
      Validators.required,
      Validators.min(1900),
      Validators.max(2050),
      numberValidator()
    ]],
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
    fenceLineDistance: ['', [
      Validators.min(1),
      Validators.max(99999),
      wholeNumberValidator(),
    ]],
    fenceLineUomCode: [null],
    comments: ['', Validators.maxLength(400)],
    programSystemCode: [null, Validators.required],
    exitGasVelocity: [null, [
      Validators.pattern('^[0-9]{0,5}([\.][0-9]{1,3})?$'),
    ]],
    exitGasVelocityUomCode: [null],
    exitGasFlowRate: [null, [
      Validators.pattern('^[0-9]{0,8}([\.][0-9]{1,8})?$'),
    ]],
    exitGasFlowUomCode: [null],

    fugitiveHeight: ['', [
      wholeNumberValidator(),
      Validators.min(0),
      Validators.max(500),
    ]],
    fugitiveHeightUomCode: [null],
    fugitiveWidth: ['', [
      wholeNumberValidator(),
      Validators.min(1),
      Validators.max(10000),
    ]],
    fugitiveWidthUomCode: [null],
    fugitiveLength: ['', [
      wholeNumberValidator(),
      Validators.min(1),
      Validators.max(10000),
    ]],
    fugitiveLengthUomCode: [null],
    fugitiveAngle: ['', [
      Validators.max(179),
      Validators.min(0),
      wholeNumberValidator()
    ]],
    fugitiveLine1Latitude: ['', [
      Validators.required,
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-90),
      Validators.max(90)
    ]],
    fugitiveLine1Longitude: ['', [
      Validators.required,
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-180),
      Validators.max(180)
    ]],
    fugitiveLine2Latitude: ['', [
      Validators.required,
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-90),
      Validators.max(90)
      ]],
    fugitiveLine2Longitude: ['', [
      Validators.required,
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-180),
      Validators.max(180)
    ]],

    stackHeight: ['', [
      Validators.required,
      Validators.min(1),
      Validators.max(1300),
      Validators.pattern('^[0-9]{0,5}([\.][0-9]{1,3})?$')
    ]],
    stackHeightUomCode: [null],
    stackDiameter: ['', [
      Validators.required,
      Validators.min(0.1),
      Validators.max(100),
      Validators.pattern('^[0-9]{0,5}([\.][0-9]{1,3})?$')
    ]],
    stackDiameterUomCode: [null],
    exitGasTemperature: ['', [
      Validators.required,
      Validators.min(-30),
      Validators.max(4000),
      Validators.pattern('^\-?[0-9]+$')
    ]]
  }, { validators: [
    this.exitFlowConsistencyCheck(),
    this.stackDiameterCheck(),
    this.exitGasFlowCheck(),
    this.uomCheck()
    ]
  });

  releasePointTypeCode: BaseCodeLookup[];
  programSystemCode: BaseCodeLookup[];
  operatingStatusValues: BaseCodeLookup[];
  uomValues: UnitMeasureCode[];
  flowUomValues: UnitMeasureCode[];
  velocityUomValues: UnitMeasureCode[];

  constructor(
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    private fb: FormBuilder) { }

  ngOnInit() {

    this.lookupService.retrieveReleaseTypeCode()
    .subscribe(result => {
      this.releasePointTypeCode = result;
    });

    this.lookupService.retrieveProgramSystemTypeCode()
    .subscribe(result => {
      this.programSystemCode = result;
    });

    this.lookupService.retrieveOperatingStatus()
    .subscribe(result => {
      this.operatingStatusValues = result;
    });

    this.lookupService.retrieveUom()
    .subscribe(result => {
      this.uomValues = result.filter(val => String(val.code) === 'FT');
      this.flowUomValues = result.filter(val => String(val.code).startsWith('ACF'));
      this.velocityUomValues = result.filter(val => String(val.code).startsWith('FP'));
    });

    this.setFormValidation();
  }

  ngOnChanges() {

    this.releasePointForm.reset(this.releasePoint);
  }

  onChange(newValue) {
    if (newValue) {
      this.releasePointForm.controls.statusYear.reset();
    }
  }

  releasePointType() {
    if (this.releasePointForm.controls.typeCode.value !== null) {
      this.releaseType = this.releasePointForm.get('typeCode').value.description;
      if (this.releaseType === 'Fugitive') {
        return true;
      }
      return false;
    }
  }

  // reset form fields and set validation based on release point type
  setFormValidation() {
    this.releasePointType();
    this.setGasFlowUomValidation();
    this.setVelocityUomValidation();
    this.setGasFlowRangeValidation();
    this.setGasVelocityRangeValidation();

    this.releasePointForm.controls.fenceLineUomCode.setValue({ code: 'FT' });

    if (this.releaseType === 'Fugitive') {
      this.releasePointForm.controls.fugitiveLengthUomCode.setValue({ code: 'FT' });
      this.releasePointForm.controls.fugitiveWidthUomCode.setValue({ code: 'FT' });
      this.releasePointForm.controls.fugitiveHeightUomCode.setValue({ code: 'FT' });
      this.releasePointForm.controls.fugitiveLine1Latitude.enable();
      this.releasePointForm.controls.fugitiveLine2Latitude.enable();
      this.releasePointForm.controls.fugitiveLine1Longitude.enable();
      this.releasePointForm.controls.fugitiveLine2Longitude.enable();
      this.releasePointForm.controls.stackHeight.disable();
      this.releasePointForm.controls.stackDiameter.disable();
      this.releasePointForm.controls.exitGasTemperature.disable();
      this.releasePointForm.controls.stackHeightUomCode.disable();
      this.releasePointForm.controls.stackDiameterUomCode.disable();
      this.releasePointForm.controls.stackHeight.reset();
      this.releasePointForm.controls.stackDiameter.reset();
      this.releasePointForm.controls.exitGasTemperature.reset();
      this.releasePointForm.controls.stackHeightUomCode.reset();
      this.releasePointForm.controls.stackDiameterUomCode.reset();
    } else {
      this.releasePointForm.controls.stackHeightUomCode.setValue({ code: 'FT' });
      this.releasePointForm.controls.stackDiameterUomCode.setValue({ code: 'FT' });
      this.releasePointForm.controls.stackHeight.enable();
      this.releasePointForm.controls.stackDiameter.enable();
      this.releasePointForm.controls.exitGasTemperature.enable();
      this.releasePointForm.controls.fugitiveLine1Latitude.disable();
      this.releasePointForm.controls.fugitiveLine2Latitude.disable();
      this.releasePointForm.controls.fugitiveLine1Longitude.disable();
      this.releasePointForm.controls.fugitiveLine2Longitude.disable();
      this.releasePointForm.controls.fugitiveLine1Latitude.reset();
      this.releasePointForm.controls.fugitiveLine2Latitude.reset();
      this.releasePointForm.controls.fugitiveLine1Longitude.reset();
      this.releasePointForm.controls.fugitiveLine2Longitude.reset();
      this.releasePointForm.controls.fugitiveLength.reset();
      this.releasePointForm.controls.fugitiveWidth.reset();
      this.releasePointForm.controls.fugitiveHeight.reset();
      this.releasePointForm.controls.fugitiveAngle.reset();
      this.releasePointForm.controls.fugitiveLengthUomCode.reset();
      this.releasePointForm.controls.fugitiveWidthUomCode.reset();
      this.releasePointForm.controls.fugitiveHeightUomCode.reset();
    }
  }

  setGasFlowUomValidation() {
    if (this.releasePointForm.controls.exitGasFlowRate.value !== null && this.releasePointForm.controls.exitGasFlowRate.value !== '') {
      this.releasePointForm.controls.exitGasFlowUomCode.setValidators([Validators.required]);
      this.releasePointForm.controls.exitGasFlowRate.updateValueAndValidity();
      this.releasePointForm.controls.exitGasFlowUomCode.updateValueAndValidity();
    } else {
      this.releasePointForm.controls.exitGasFlowUomCode.setValidators([]);
      this.releasePointForm.controls.exitGasFlowUomCode.reset();
    }
  }

  setVelocityUomValidation() {
    if (this.releasePointForm.controls.exitGasVelocity.value !== null && this.releasePointForm.controls.exitGasVelocity.value !== '') {
      this.releasePointForm.controls.exitGasVelocityUomCode.setValidators([Validators.required]);
      this.releasePointForm.controls.exitGasVelocity.updateValueAndValidity();
      this.releasePointForm.controls.exitGasVelocityUomCode.updateValueAndValidity();
    } else {
      this.releasePointForm.controls.exitGasVelocityUomCode.setValidators([]);
      this.releasePointForm.controls.exitGasVelocityUomCode.reset();
    }
  }

  setGasFlowRangeValidation() {
    if (this.releasePointForm.controls.exitGasFlowUomCode.value !== null) {
      this.releasePointType();

      if (this.releasePointForm.controls.exitGasFlowUomCode.value.code === 'ACFS') {
        if (this.releaseType === 'Fugitive') {
          this.releasePointForm.controls.exitGasFlowRate.setValidators([Validators.min(0), Validators.max(200000)]);
        } else {
          this.releasePointForm.controls.exitGasFlowRate.setValidators([Validators.min(0.00000001), Validators.max(200000)]);
        }
        this.releasePointForm.controls.exitGasFlowRate.updateValueAndValidity();
        this.releasePointForm.controls.exitGasFlowUomCode.updateValueAndValidity();
      } else {
        if (this.releaseType === 'Fugitive') {
          this.releasePointForm.controls.exitGasFlowRate.setValidators([Validators.min(0), Validators.max(12000000)]);
        } else {
          this.releasePointForm.controls.exitGasFlowRate.setValidators([Validators.min(0.00000001), Validators.max(12000000)]);
        }
        this.releasePointForm.controls.exitGasFlowRate.updateValueAndValidity();
        this.releasePointForm.controls.exitGasFlowUomCode.updateValueAndValidity();
      }
    }
  }

  setGasVelocityRangeValidation() {
    if (this.releasePointForm.controls.exitGasVelocityUomCode.value !== null) {
      this.releasePointType();

      if (this.releasePointForm.controls.exitGasVelocityUomCode.value.code === 'FPS') {
        if (this.releaseType === 'Fugitive') {
          this.releasePointForm.controls.exitGasVelocity.setValidators([Validators.min(0), Validators.max(400)]);
        } else {
          this.releasePointForm.controls.exitGasVelocity.setValidators([Validators.min(0.001), Validators.max(1500)]);
        }
        this.releasePointForm.controls.exitGasVelocity.updateValueAndValidity();
        this.releasePointForm.controls.exitGasVelocityUomCode.updateValueAndValidity();
      } else {
        if (this.releaseType === 'Fugitive') {
          this.releasePointForm.controls.exitGasVelocity.setValidators([Validators.min(0), Validators.max(24000)]);
        } else {
          this.releasePointForm.controls.exitGasVelocity.setValidators([Validators.min(0.060), Validators.max(90000)]);
        }
        this.releasePointForm.controls.exitGasVelocity.updateValueAndValidity();
        this.releasePointForm.controls.exitGasVelocityUomCode.updateValueAndValidity();
      }
    }
  }

  // Check flow and velocity uom
  uomCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const velocityUom = control.get('exitGasVelocityUomCode');
      const flowUom = control.get('exitGasFlowUomCode');

      if ((velocityUom.value !== null && flowUom.value !== null) &&
        (velocityUom.value.code.charAt(velocityUom.value.code.length-1) !== flowUom.value.code.charAt(flowUom.value.code.length-1))) {
          return { invalidUnits: true };
        }
      return null;
    };
  }

  // Exit gas flow rate or Exit gas velocity must be entered
  exitGasFlowCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const flowRate = control.get('exitGasFlowRate');
      const velocity = control.get('exitGasVelocity');

      if (this.releaseType !== 'Fugitive') {
        if ((flowRate.value === null || flowRate.value === '') && (velocity.value === null || velocity.value === '')) {
          return { invalidVelocity: true };
        }
        return null;
      }
    };
  }

  // Stack diameter must be less than stack height.
  stackDiameterCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const diameter = control.get('stackDiameter'); // ft
      const height = control.get('stackHeight'); // ft

      if (diameter.value > 0 && height.value > 0) {
        return (Number(height.value) <= Number(diameter.value)) ? { invalidDiameter: true } : null;
      }
      return null;
    };
  }

  // Exit gas flow input must be within +/-5% of computed flow
  exitFlowConsistencyCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const diameter = control.get('stackDiameter'); // ft
      const exitVelocity = control.get('exitGasVelocity'); // fps/fpm
      const exitFlowRate = control.get('exitGasFlowRate'); // acfs/acfm
      let valid = true;

      if ((diameter.value !== null) &&
        (exitVelocity.value !== null && exitVelocity.value !== '') &&
        (exitFlowRate.value !== null && exitFlowRate.value !== '')) {

        const computedArea = (((1/4)*Math.PI)*(Math.pow(diameter.value, 2))); // sf
        const computedFlow = (computedArea*exitVelocity.value); // cfs

        // Compare to value with 0.1 precision
        const upperLimit = (Math.round((computedFlow*1.05)*10))/10; // cfs
        const lowerLimit = (Math.round((computedFlow*0.95)*10))/10; // cfs

        const actualFlowRate = (Math.round(exitFlowRate.value*10))/10; // acfs

        if (actualFlowRate > upperLimit || actualFlowRate < lowerLimit) {
          valid = false;

          // If user enters 0.1 and calculated flow is less than 0.1
          // Min allowable actual flow rate user can enter is 0.1
          if ((actualFlowRate === 0.1 && upperLimit < 0.1)) {
            valid = true;
          }
          return valid ? null : { invalidFlowRate: true };
        }
      }
      return null;
    };
  }
}
