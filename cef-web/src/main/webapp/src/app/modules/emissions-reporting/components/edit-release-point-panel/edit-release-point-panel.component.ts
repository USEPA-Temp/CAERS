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
      Validators.required,
      Validators.maxLength(200)
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
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ]],
    fenceLineUomCode: [{ value: null }],
    comments: ['', Validators.maxLength(2000)],
    programSystemCode: [null],

    fugitiveHeight: ['',
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ],
    fugitiveHeightUomCode: [{ value: null }],
    fugitiveWidth: ['',
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ],
    fugitiveWidthUomCode: [{ value: null }],
    fugitiveLength: ['',
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ],
    fugitiveLengthUomCode: [{ value: null }],
    fugitiveAngle: ['', [
      Validators.max(360),
      Validators.min(0),
    ]],
    fugitiveLine1Latitude: ['', [
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-90),
      Validators.max(90),
    ]],
    fugitiveLine1Longitude: ['', [
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-180),
      Validators.max(180),
    ]],
    fugitiveLine2Latitude: ['', [
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-90),
      Validators.max(90),
    ]],
    fugitiveLine2Longitude: ['', [
      Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
      Validators.min(-180),
      Validators.max(180),
    ]],

    stackHeight: ['', [
      Validators.max(1300),
      Validators.min(1),
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ]],
    stackHeightUomCode: [{ value: null }],
    stackDiameter: ['', [
      Validators.max(100),
      Validators.min(0.1),
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ]],
    stackDiameterUomCode: [{ value: null }],
    exitGasVelocity: ['', [
      Validators.max(600),
      Validators.min(0.1),
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ]],
    exitGasVelocityUomCode: [{ value: null }],
    exitGasTemperature: ['', [
      wholeNumberValidator(),
      Validators.min(30),
      Validators.max(3500),
    ]],
    exitGasFlowRate: ['', [
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

  releasePointType() {
    if (this.releasePointForm.controls.typeCode.value !== null) {
      const releaseTypeControl = this.releasePointForm.get('typeCode');
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


  // Custom Validators
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
