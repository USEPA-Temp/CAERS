import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormBuilder, Validators } from '@angular/forms';
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
    description: ['', [
      Validators.required,
      Validators.maxLength(200)
    ]],
    statusYear: ['', [
      Validators.min(1900),
      Validators.max(2050),
      numberValidator()
    ]],
    stackHeight: ['', [
      Validators.required,
      Validators.max(99999.999),
      Validators.min(0.001),
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ]],
    stackHeightUomCode: [null, Validators.required],
    stackDiameter: ['', [
      Validators.required,
      Validators.max(999.999),
      Validators.min(0.001),
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ]],
    stackDiameterUomCode: [null, Validators.required],
    exitGasVelocity: ['', [
      Validators.required,
      Validators.max(99999.999),
      Validators.min(0.001),
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?$')
    ]],
    exitGasVelocityUomCode: [null, Validators.required],
    exitGasTemperature: ['', [
      wholeNumberValidator(),
      Validators.min(30),
      Validators.max(3500),
    ]],
    exitGasFlowRate: ['', [
      Validators.required,
      Validators.max(99999999.99999999),
      Validators.min(0.00000001),
      Validators.pattern('^[0-9]{1,8}([\.][0-9]{1,8})?$')
    ]],
    exitGasFlowUomCode: [null, Validators.required],
    typeCode: [null, Validators.required],
    operatingStatusCode: [null, Validators.required],
    programSystemCode: [null],
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
    comments: ['', Validators.maxLength(2000)]
  });

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

  }

  ngOnChanges() {

    this.releasePointForm.reset(this.releasePoint);
  }

}
