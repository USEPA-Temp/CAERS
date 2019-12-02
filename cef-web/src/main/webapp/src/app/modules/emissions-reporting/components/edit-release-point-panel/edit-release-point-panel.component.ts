import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormBuilder, Validators } from '@angular/forms';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';

@Component({
  selector: 'app-edit-release-point-panel',
  templateUrl: './edit-release-point-panel.component.html',
  styleUrls: ['./edit-release-point-panel.component.scss']
})
export class EditReleasePointPanelComponent implements OnInit, OnChanges {
  @Input() releasePoint: ReleasePoint;

  releasePointForm = this.fb.group({
    releasePointIdentifier: ['', Validators.required],
    stackHeight: ['', [
      Validators.required,
      Validators.pattern('^[0-9]{1,5}([\.][0-9]{1,3})?')
    ]],
    stackHeightUomCode: [null, Validators.required],
    stackDiameter: ['', [
      Validators.required,
      Validators.pattern('^[0-9]{1,3}([\.][0-9]{1,3})?$')
    ]],
    stackDiameterUomCode: [null, Validators.required],
    exitGasVelocity: ['', [
      Validators.required,
      Validators.pattern('^[0-9]{0,5}([\.][0-9]{1,3})?$')
    ]],
    exitGasVelocityUomCode: [null, Validators.required],
    typeCode: [null, Validators.required],
    description: ['', Validators.required],
    exitGasTemperature: ['', [
      Validators.pattern('^[0-9]{1,4}')
    ]],
    exitGasFlowRate: ['', [
      Validators.required,
      Validators.pattern('^[0-9]{0,8}([\.][0-9]{1,8})?$')
    ]],
    exitGasFlowUomCode: [null, Validators.required],
    latitude: ['', [
      Validators.required,
      Validators.pattern('^[0-9]{0,4}([\.][0-9]{1,6})?$')
    ]],
    longitude: ['', [
      Validators.required,
      Validators.pattern('^-?([0-9]{0,4})([\.][0-9]{1,6})?$')
    ]],
    operatingStatusCode: [null, Validators.required],
    statusYear: ['', [
      Validators.pattern('[0-9]{4}')
    ]],
    programSystemCode: [null, [
    ]],
    fugitiveLine1Latitude: ['', [
      Validators.pattern('^[0-9]{0,4}([\.][0-9]{1,6})?$')
    ]],
    fugitiveLine1Longitude: ['', [
      Validators.pattern('^-?([0-9]{0,4})([\.][0-9]{1,6})?$')
    ]],
    fugitiveLine2Latitude: ['', [
      Validators.pattern('^[0-9]{0,4}([\.][0-9]{1,6})?$')
    ]],
    fugitiveLine2Longitude: ['', [
      Validators.pattern('^-?([0-9]{0,4})([\.][0-9]{1,6})?$')
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
