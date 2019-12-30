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

  releasePointForm = this.fb.group({
    releasePointIdentifier: ['', [
      Validators.required,
      Validators.maxLength(20)
    ]],
    typeCode: [null, Validators.required],
    description: ['', [
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
    fenceLineUomCode: [{ value: null }],
    comments: ['', Validators.maxLength(2000)],
    programSystemCode: [null, Validators.required],

    fugitiveHeight: ['', [
      wholeNumberValidator(),
      Validators.min(0),
      Validators.max(500),
    ]],
    fugitiveHeightUomCode: [{ value: null }],
    fugitiveWidth: ['', [
      wholeNumberValidator(),
      Validators.min(1),
      Validators.max(10000),
    ]],
    fugitiveWidthUomCode: [{ value: null }],
    fugitiveLength: ['', [
      wholeNumberValidator(),
      Validators.min(1),
      Validators.max(10000),
    ]],
    fugitiveLengthUomCode: [{ value: null }],
    fugitiveAngle: ['', [
      Validators.max(179),
      Validators.min(0),
    ]],
    fugitiveLine1Latitude: ['', [
      // Validators.required,
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-90),
      Validators.max(90),
    ]],
    fugitiveLine1Longitude: ['', [
      // Validators.required,
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-180),
      Validators.max(180),
    ]],
    fugitiveLine2Latitude: ['', [
      // Validators.required,
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-90),
      Validators.max(90),
    ]],
    fugitiveLine2Longitude: ['', [
      // Validators.required,
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-180),
      Validators.max(180),
    ]],

    stackHeight: ['', [
      // Validators.required,
      Validators.max(1300),
      Validators.min(1),
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ]],
    stackHeightUomCode: [{ value: null }],
    stackDiameter: ['', [
      // Validators.required,
      Validators.max(100),
      Validators.min(0.1),
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ]],
    stackDiameterUomCode: [{ value: null }],
    exitGasVelocity: ['', [
      // Validators.required,
      Validators.max(600),
      Validators.min(0.1),
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ]],
    exitGasVelocityUomCode: [{ value: null }],
    exitGasTemperature: ['', [
      // Validators.required,
      wholeNumberValidator(),
      Validators.min(30),
      Validators.max(3500),
    ]],
    exitGasFlowRate: ['', [
      // Validators.required,
      Validators.max(200000),
      Validators.min(0.1),
      Validators.pattern('^[0-9]{1,8}([\.][0-9]{1,8})?$'),
    ]],
    exitGasFlowUomCode: [{ value: null }]
  }, { validators: [this.exitFlowCheck(), this.stackDiameterCheck()] });

  releasePointTypeCode: BaseCodeLookup[];
  programSystemCode: BaseCodeLookup[];
  operatingStatusValues: BaseCodeLookup[];
  uomValues: UnitMeasureCode[];

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
      this.uomValues = result;
    });

    // set default UoM
    this.releasePointForm.controls.fenceLineUomCode.setValue({ code: 'FT' });
    this.releasePointForm.controls.fugitiveLengthUomCode.setValue({ code: 'FT' });
    this.releasePointForm.controls.fugitiveWidthUomCode.setValue({ code: 'FT' });
    this.releasePointForm.controls.fugitiveHeightUomCode.setValue({ code: 'FT' });
    this.releasePointForm.controls.exitGasVelocityUomCode.setValue({ code: 'FPS' });
    this.releasePointForm.controls.exitGasFlowUomCode.setValue({ code: 'ACFS' });
    this.releasePointForm.controls.stackHeightUomCode.setValue({ code: 'FT' });
    this.releasePointForm.controls.stackDiameterUomCode.setValue({ code: 'FT' });

    this.releasePointType();
  }

  ngOnChanges() {

    this.releasePointForm.reset(this.releasePoint);
  }

  // reset form fields based on release point type
  releasePointType() {
    if (this.releasePointForm.controls.typeCode.value !== null) {
      const releaseTypeControl = this.releasePointForm.get('typeCode');
      this.rpTypeRequiredFields();

      if (releaseTypeControl.value.description === 'Fugitive') {
        this.releasePointForm.controls.exitGasVelocity.reset();
        this.releasePointForm.controls.exitGasFlowRate.reset();
        this.releasePointForm.controls.stackDiameter.reset();
        this.releasePointForm.controls.stackHeight.reset();
        this.releasePointForm.controls.exitGasTemperature.reset();
        return true;
      }
      this.releasePointForm.controls.fugitiveLength.reset();
      this.releasePointForm.controls.fugitiveWidth.reset();
      this.releasePointForm.controls.fugitiveHeight.reset();
      this.releasePointForm.controls.fugitiveAngle.reset();
      this.releasePointForm.controls.fugitiveLine1Latitude.reset();
      this.releasePointForm.controls.fugitiveLine2Latitude.reset();
      this.releasePointForm.controls.fugitiveLine1Longitude.reset();
      this.releasePointForm.controls.fugitiveLine2Longitude.reset();
    }
    return false;
  }

  // set form field required based on release point type
  rpTypeRequiredFields() {
    const releaseTypeControl = this.releasePointForm.get('typeCode');
    const exitGasTemperature = this.releasePointForm.get('exitGasTemperature');
    const stackHeight = this.releasePointForm.get('stackHeight');
    const stackDiameter = this.releasePointForm.get('stackDiameter');
    const exitGasFlowRate = this.releasePointForm.get('exitGasFlowRate');
    const exitGasVelocity = this.releasePointForm.get('exitGasVelocity');
    const fugitiveLine1Latitude = this.releasePointForm.get('fugitiveLine1Latitude');
    const fugitiveLine2Latitude = this.releasePointForm.get('fugitiveLine2Latitude');
    const fugitiveLine1Longitude = this.releasePointForm.get('fugitiveLine1Longitude');
    const fugitiveLine2Longitude = this.releasePointForm.get('fugitiveLine2Longitude');

    if (releaseTypeControl.value.description !== 'Fugitive') {
      stackHeight.setValidators([Validators.required]);
      stackDiameter.setValidators([Validators.required]);
      exitGasTemperature.setValidators([Validators.required]);
      exitGasFlowRate.setValidators([Validators.required]);
      exitGasVelocity.setValidators([Validators.required]);
      fugitiveLine1Latitude.setValidators(null);
      fugitiveLine2Latitude.setValidators(null);
      fugitiveLine1Longitude.setValidators(null);
      fugitiveLine2Longitude.setValidators(null);
    } else {
      fugitiveLine1Latitude.setValidators([Validators.required]);
      fugitiveLine2Latitude.setValidators([Validators.required]);
      fugitiveLine1Longitude.setValidators([Validators.required]);
      fugitiveLine2Longitude.setValidators([Validators.required]);
      stackHeight.setValidators(null);
      stackDiameter.setValidators(null);
      exitGasTemperature.setValidators(null);
      exitGasFlowRate.setValidators(null);
      exitGasVelocity.setValidators(null);
    }
  }

  // Stack diameter must be less than stack height
  stackDiameterCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const diameter = control.get('stackDiameter'); // ft
      const height = control.get('stackHeight'); // ft

      if (Number(height.value) > 0) {
        if ((Number(diameter.value) === 0) || (Number(height.value) <= Number(diameter.value))) {
          return diameter.value === '' ? null : { stackDiameter: { value: this.releasePoint.stackDiameter } };
        }
      }
      return null;
    };
  }

  // Exit gas flow input must be within +/-5% of computed flow
  exitFlowCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const diameter = control.get('stackDiameter'); // ft
      const exitVelocity = control.get('exitGasVelocity'); // fps
      const exitFlowRate = control.get('exitGasFlowRate'); // acfs

      const computedArea = (((1/4)*Math.PI)*(Math.pow(diameter.value, 2))); // sf

      const computedFlow = (computedArea*exitVelocity.value); // cfs

      // Compare to value with 0.1 precision
      const upperLimit = (Math.round((computedFlow*1.05)*10))/10; // cfs
      const lowerLimit = (Math.round((computedFlow*0.95)*10))/10; // cfs

      const actualFlowRate = (Math.round(exitFlowRate.value*10))/10; // acfs

      if (exitFlowRate.value > 0) {
        if ((actualFlowRate > upperLimit || actualFlowRate < lowerLimit)) {
          return exitFlowRate.value === '' ? null : { exitGasFlowRate: { value: this.releasePoint.exitGasFlowRate } };
        }
        return null;
      }
    };
  }
}
