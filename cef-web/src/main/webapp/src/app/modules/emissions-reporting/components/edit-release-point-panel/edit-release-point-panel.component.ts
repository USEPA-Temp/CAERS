import {Component, OnInit, Input, OnChanges} from '@angular/core';
import {LookupService} from 'src/app/core/services/lookup.service';
import {FormBuilder, Validators, ValidationErrors, ValidatorFn, FormGroup} from '@angular/forms';
import {BaseCodeLookup} from 'src/app/shared/models/base-code-lookup';
import {FormUtilsService} from 'src/app/core/services/form-utils.service';
import {ReleasePoint} from 'src/app/shared/models/release-point';
import {UnitMeasureCode} from 'src/app/shared/models/unit-measure-code';
import {numberValidator} from 'src/app/modules/shared/directives/number-validator.directive';
import {wholeNumberValidator} from 'src/app/modules/shared/directives/whole-number-validator.directive';
import {FacilitySite} from 'src/app/shared/models/facility-site';
import {ActivatedRoute} from '@angular/router';
import {EisLatLongToleranceLookup} from 'src/app/shared/models/eis-latlong-tolerance-lookup';
import {ReleasePointService} from 'src/app/core/services/release-point.service';
import {InventoryYearCodeLookup} from 'src/app/shared/models/inventory-year-code-lookup';
import {legacyItemValidator} from 'src/app/modules/shared/directives/legacy-item-validator.directive';
import {OperatingStatus} from 'src/app/shared/enums/operating-status';

@Component({
    selector: 'app-edit-release-point-panel',
    templateUrl: './edit-release-point-panel.component.html',
    styleUrls: ['./edit-release-point-panel.component.scss']
})
export class EditReleasePointPanelComponent implements OnInit, OnChanges {

    // TODO: should consider moving these validations into a separate file
    // numbers with length of 8 precision of 3
    readonly numberPattern83 = '^[0-9]{0,5}([\.][0-9]{1,3})?$';
    // numbers with length of 16 precision of 8
    readonly numberPattern168 = '^[0-9]{0,8}([\.][0-9]{0,8})?([eE]{1}[-+]?[0-9]+)?$';

    @Input() releasePoint: ReleasePoint;
    @Input() year: number;
    releasePointIdentifiers: string[] = [];
    readonly fugitiveType = 'Fugitive';
    readonly stackType = 'Stack'
    facilitySite: FacilitySite;
    releaseType: string;
    eisProgramId: string;
    facilityOpCode: BaseCodeLookup;
    diameterCheckHeightWarning: string;
    diameterCheckFlowAndVelWarning: string;
    diameterOrLengthAndWidthMessage: string;
    velAndFlowCheckDiameterWarning: string;
    calculatedVelocity: string;
    calculatedFlowRate: string;
    calculatedFlowRateUom: string;
    calculatedVelocityUom: string;
    minVelocity: number;
    maxVelocity: number;
    coordinateTolerance: EisLatLongToleranceLookup;
    tolerance: number;

    releasePointForm = this.fb.group({
        releasePointIdentifier: ['', [
            Validators.required,
            Validators.maxLength(20)
        ]],
        operatingStatusCode: [null, Validators.required],
        statusYear: ['', [
            Validators.required,
            Validators.min(1900),
            Validators.max(2050),
            numberValidator()
        ]],
        typeCode: [null],
        description: ['', [
            Validators.maxLength(200),
            this.requiredIfOperating()
        ]],
        latitude: [null, [
            Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
            Validators.min(-90),
            Validators.max(90),
        ]],
        longitude: [null, [
            Validators.pattern('^-?[0-9]{1,3}([\.][0-9]{1,6})?$'),
            Validators.min(-180),
            Validators.max(180),
        ]],
        fenceLineDistance: ['', [
            Validators.min(0),
            Validators.max(99999),
            wholeNumberValidator(),
        ]],
        fenceLineUomCode: [null],
        comments: [null, Validators.maxLength(400)],
        exitGasVelocity: [null, Validators.pattern(this.numberPattern83)],
        exitGasVelocityUomCode: [null],
        exitGasFlowRate: [null, Validators.pattern(this.numberPattern168)],
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
        stackWidth: [null, [
            Validators.min(.1),
            Validators.max(100),
        ]],
        stackWidthUomCode: [null],
        fugitiveLength: ['', [
            wholeNumberValidator(),
            Validators.min(1),
            Validators.max(10000),
        ]],
        fugitiveLengthUomCode: [null],
        stackLength: [null, [
            Validators.min(.1),
            Validators.max(100),
        ]],
        stackLengthUomCode: [null],
        fugitiveAngle: ['', [
            Validators.max(89),
            Validators.min(0),
            wholeNumberValidator()
        ]],
        fugitiveLine1Latitude: [],
        fugitiveLine1Longitude: [],
        fugitiveLine2Latitude: [],
        fugitiveLine2Longitude: [],
        stackHeight: ['', [
            Validators.min(1),
            Validators.max(1300),
            Validators.pattern(this.numberPattern83),
            this.requiredIfOperating()
        ]],
        stackHeightUomCode: [null, [
            this.requiredIfOperating()
        ]],
        stackDiameter: [null, [
            Validators.min(0.001),
            Validators.max(300),
            Validators.pattern(this.numberPattern83),
        ]],
        stackDiameterUomCode: [null, []],
        exitGasTemperature: ['', [
            Validators.min(-30),
            Validators.max(4000),
            Validators.pattern('^\-?[0-9]+$'),
            this.requiredIfOperating()
        ]]
    }, {
        validators: [
            this.exitFlowConsistencyCheck(),
            this.stackDiameterCheck(),
            this.exitGasFlowCheck(),
            this.exitVelocityCheck(),
            this.coordinateToleranceCheck(),
            this.stackVelAndFlowCheckForDiameter(),
            this.stackDiameterCheckForVelAndFlow(),
            this.facilitySiteStatusCheck(),
            this.exitGasFlowUomCheck(),
            this.exitGasVelocityUomCheck(),
            this.releasePointIdentifierCheck(),
            this.latLongRequiredCheck(),
            this.stackDiameterOrStackWidthAndLength()
        ]
    });

    releasePointTypeCode: InventoryYearCodeLookup[];
    operatingSubFacilityStatusValues: BaseCodeLookup[];
    uomValues: UnitMeasureCode[];
    flowUomValues: UnitMeasureCode[];
    velocityUomValues: UnitMeasureCode[];

    constructor(
        private lookupService: LookupService,
        public formUtils: FormUtilsService,
        private route: ActivatedRoute,
        private fb: FormBuilder,
        private releasePointService: ReleasePointService) {
    }

    ngOnInit() {

        this.lookupService.retrieveCurrentReleaseTypeCodes(this.year)
            .subscribe(result => {
                this.releasePointTypeCode = result;
            });

        this.lookupService.retrieveSubFacilityOperatingStatus()
            .subscribe(result => {
                this.operatingSubFacilityStatusValues = result;
            });

        this.lookupService.retrieveUom()
            .subscribe(result => {
                this.uomValues = result.filter(val => String(val.code) === 'FT');
                const ftUom = this.uomValues[0];
                this.stackWidthUomCode.patchValue(ftUom);
                this.stackLengthUomCode.patchValue(ftUom);
                this.fugitiveWidthUomCode.patchValue(ftUom);
                this.fugitiveLengthUomCode.patchValue(ftUom);
                this.stackDiameterUomCode.patchValue(ftUom);
                this.stackHeightUomCode.patchValue(ftUom);
                console.log(this.fugitiveWidthUomCode.value)
                console.log(this.releasePointForm.getRawValue())
                this.flowUomValues = result.filter(val => String(val.code).startsWith('ACF'));
                this.velocityUomValues = result.filter(val => String(val.code).startsWith('FP'));
            });

        this.route.data
            .subscribe((data: { facilitySite: FacilitySite }) => {
                this.facilitySite = data.facilitySite;
                this.eisProgramId = this.facilitySite.eisProgramId;
                this.facilityOpCode = this.facilitySite.operatingStatusCode;
                this.releasePointService.retrieveForFacility(data.facilitySite.id)
                    .subscribe(releasePoints => {
                        releasePoints.forEach(rp => {
                            this.releasePointIdentifiers.push(rp.releasePointIdentifier);
                        });

                        // if a release point is being edited then filter that identifer out the list so the validator check doesnt identify it as a duplicate
                        if (this.releasePoint) {
                            this.releasePointIdentifiers = this.releasePointIdentifiers.filter(identifer => identifer.toString() !== this.releasePoint.releasePointIdentifier);
                        }

                    });
            });

        this.setFormValidation();
    }

    get stackWidth() {
        return this.releasePointForm.get('stackWidth');
    }

    get stackWidthUomCode() {
        return this.releasePointForm.get('stackWidthUomCode');
    }

    get stackLength() {
        return this.releasePointForm.get('stackLength');
    }

    get stackLengthUomCode() {
        return this.releasePointForm.get('stackLengthUomCode');
    }

    get stackDiameter() {
        return this.releasePointForm.get('stackDiameter');
    }

    get stackDiameterUomCode() {
        return this.releasePointForm.get('stackDiameterUomCode')
    }

    get stackHeight() {
        return this.releasePointForm.get('stackHeight');
    }

    get stackHeightUomCode() {
        return this.releasePointForm.get('stackHeightUomCode')
    }

    get fugitiveWidth() {
        return this.releasePointForm.get('fugitiveWidth');
    }

    get fugitiveWidthUomCode() {
        return this.releasePointForm.get('fugitiveWidthUomCode');
    }

    get fugitiveLength() {
        return this.releasePointForm.get('fugitiveLength');
    }

    get fugitiveLengthUomCode() {
        return this.releasePointForm.get('fugitiveLengthUomCode');
    }

    get typeCode() {
        return this.releasePointForm.get('typeCode');
    }

    ngOnChanges() {

        this.releasePointForm.reset(this.releasePoint);
    }

    onChange(newValue) {
        if (newValue) {
            this.releasePointForm.controls.statusYear.reset();
        }
        this.releasePointForm.controls.statusYear.markAsTouched();
        this.releasePointForm.controls.description.updateValueAndValidity();
        this.setFormValidation();
    }

    isReleasePointFugitiveType() {
        if (this.releasePointForm.controls.typeCode.value !== null) {
            this.releaseType = this.releasePointForm.get('typeCode').value.description;
            if (this.releaseType === this.fugitiveType) {
                return true;
            }
            return false;
        }
    }

    // reset form fields and set validation based on release point type
    setFormValidation() {
        this.isReleasePointFugitiveType();
        this.setGasFlowRangeValidation();
        this.setGasVelocityRangeValidation();
        this.uomRequiredCheck();

        if (this.releasePointForm.get('operatingStatusCode').value
            && this.releasePointForm.get('operatingStatusCode').value.code.includes(OperatingStatus.OPERATING)) {
            this.releasePointForm.get('typeCode').setValidators([Validators.required,
                legacyItemValidator(this.year, 'Release Point Type Code', 'description')]);
            this.releasePointForm.controls.typeCode.markAsTouched();
        } else {
            this.releasePointForm.get('typeCode').setValidators([Validators.required]);
        }
        this.releasePointForm.controls.typeCode.updateValueAndValidity();

        if (this.releaseType === this.fugitiveType) {
            this.releasePointForm.controls.stackHeight.disable();
            this.releasePointForm.controls.stackDiameter.disable();
            this.releasePointForm.controls.stackWidth.disable();
            this.releasePointForm.controls.stackWidthUomCode.disable();
            this.releasePointForm.controls.stackLength.disable();
            this.releasePointForm.controls.stackLengthUomCode.disable();
            this.releasePointForm.controls.exitGasTemperature.disable();
            this.releasePointForm.controls.stackHeightUomCode.disable();
            this.releasePointForm.controls.stackDiameterUomCode.disable();
            this.releasePointForm.controls.stackHeight.reset();
            this.releasePointForm.controls.stackDiameter.reset();
            this.releasePointForm.controls.stackWidth.reset();
            this.releasePointForm.controls.stackLength.reset();
            this.releasePointForm.controls.exitGasTemperature.reset();
            this.releasePointForm.controls.stackHeightUomCode.reset();
            this.releasePointForm.controls.stackDiameterUomCode.reset();
            this.releasePointForm.controls.stackWidthUomCode.reset();
            this.releasePointForm.controls.stackLengthUomCode.reset();
        } else {
            this.releasePointForm.controls.stackHeight.enable();
            this.releasePointForm.controls.stackDiameter.enable();
            this.releasePointForm.controls.exitGasTemperature.enable();
            this.releasePointForm.controls.stackHeightUomCode.enable();
            this.releasePointForm.controls.stackDiameterUomCode.enable();
            this.releasePointForm.controls.stackWidth.enable();
            this.releasePointForm.controls.stackLength.enable();
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

    // QA Check - exit gas flow rate range
    setGasFlowRangeValidation() {
        if (this.releasePointForm.controls.exitGasFlowUomCode.value !== null) {
            this.isReleasePointFugitiveType();

            if (this.releasePointForm.controls.exitGasFlowUomCode.value.code === 'ACFS') {
                if (this.releaseType === this.fugitiveType) {
                    this.releasePointForm.controls.exitGasFlowRate.setValidators([
                        Validators.min(0), Validators.max(200000), Validators.pattern(this.numberPattern168)]);
                } else {
                    this.releasePointForm.controls.exitGasFlowRate.setValidators([
                        Validators.min(0.00000001), Validators.max(200000), Validators.pattern(this.numberPattern168)]);
                }
                this.releasePointForm.controls.exitGasFlowRate.updateValueAndValidity();
                this.releasePointForm.controls.exitGasFlowUomCode.updateValueAndValidity();
            } else {
                if (this.releaseType === this.fugitiveType) {
                    this.releasePointForm.controls.exitGasFlowRate.setValidators([
                        Validators.min(0), Validators.max(12000000), Validators.pattern(this.numberPattern168)]);
                } else {
                    this.releasePointForm.controls.exitGasFlowRate.setValidators([
                        Validators.min(0.00000001), Validators.max(12000000), Validators.pattern(this.numberPattern168)]);
                }
                this.releasePointForm.controls.exitGasFlowRate.updateValueAndValidity();
                this.releasePointForm.controls.exitGasFlowUomCode.updateValueAndValidity();
            }
        }
    }

    // QA Check - exit gas velocity range
    setGasVelocityRangeValidation() {
        if (this.releasePointForm.controls.exitGasVelocityUomCode.value !== null) {
            this.isReleasePointFugitiveType();

            if (this.releasePointForm.controls.exitGasVelocityUomCode.value.code === 'FPS') {
                if (this.releaseType === this.fugitiveType) {
                    this.releasePointForm.controls.exitGasVelocity.setValidators([
                        Validators.min(0), Validators.max(400), Validators.pattern(this.numberPattern83)]);
                } else {
                    this.releasePointForm.controls.exitGasVelocity.setValidators([
                        Validators.min(0.001), Validators.max(1500), Validators.pattern(this.numberPattern83)]);
                }
                this.releasePointForm.controls.exitGasVelocity.updateValueAndValidity();
                this.releasePointForm.controls.exitGasVelocityUomCode.updateValueAndValidity();
            } else {
                if (this.releaseType === this.fugitiveType) {
                    this.releasePointForm.controls.exitGasVelocity.setValidators([
                        Validators.min(0), Validators.max(24000), Validators.pattern(this.numberPattern83)]);
                } else {
                    this.releasePointForm.controls.exitGasVelocity.setValidators([
                        Validators.min(0.060), Validators.max(90000), Validators.pattern(this.numberPattern83)]);
                }
                this.releasePointForm.controls.exitGasVelocity.updateValueAndValidity();
                this.releasePointForm.controls.exitGasVelocityUomCode.updateValueAndValidity();
            }
        }
    }

    // sets uom to required if optional fields have values
    // Commented out while automatically setting UoM to feet.
    // This should be uncommented if other UoMs are allowed
    uomRequiredCheck() {
        // this.releasePointForm.get('fenceLineDistance').valueChanges
        //     .subscribe(value => {
        //         if (this.releasePointForm.get('fenceLineDistance').value) {
        //                 this.releasePointForm.get('fenceLineUomCode').setValidators([Validators.required]);
        //                 this.releasePointForm.controls.fenceLineUomCode.updateValueAndValidity();
        //         } else {
        //                 this.releasePointForm.get('fenceLineUomCode').setValidators(null);
        //                 this.releasePointForm.controls.fenceLineUomCode.updateValueAndValidity();
        //         }
        //     });

        // this.releasePointForm.get('fugitiveLength').valueChanges
        //     .subscribe(value => {
        //       if (this.releasePointForm.get('fugitiveLength').value) {
        //         this.releasePointForm.get('fugitiveLengthUomCode').setValidators([Validators.required]);
        //         this.releasePointForm.controls.fugitiveLengthUomCode.updateValueAndValidity();
        //       } else {
        //         this.releasePointForm.get('fugitiveLengthUomCode').setValidators(null);
        //         this.releasePointForm.controls.fugitiveLengthUomCode.updateValueAndValidity();
        //       }
        //     });

        // this.releasePointForm.get('fugitiveWidth').valueChanges
        // .subscribe(value => {
        //     if (this.releasePointForm.get('fugitiveWidth').value) {
        //       this.releasePointForm.get('fugitiveWidthUomCode').setValidators([Validators.required]);
        //       this.releasePointForm.controls.fugitiveWidthUomCode.updateValueAndValidity();
        //     } else {
        //       this.releasePointForm.get('fugitiveWidthUomCode').setValidators(null);
        //       this.releasePointForm.controls.fugitiveWidthUomCode.updateValueAndValidity();
        //     }
        // });

        // this.releasePointForm.get('fugitiveHeight').valueChanges
        //     .subscribe(value => {
        //         if (this.releasePointForm.get('fugitiveHeight').value) {
        //             this.releasePointForm.get('fugitiveHeightUomCode').setValidators([Validators.required]);
        //             this.releasePointForm.controls.fugitiveHeightUomCode.updateValueAndValidity();
        //         } else {
        //               this.releasePointForm.get('fugitiveHeightUomCode').setValidators(null);
        //               this.releasePointForm.controls.fugitiveHeightUomCode.updateValueAndValidity();
        //         }
        //     });
    }

    // sets lat and long to required if one of the optional fields has a value
    latLongRequiredCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            if (control.get('latitude').value && !control.get('longitude').value) {
                control.get('longitude').markAsTouched();
                control.get('longitude').setErrors({requiredCoordinate: true});
            } else if (control.get('longitude').value && !control.get('latitude').value) {
                control.get('latitude').markAsTouched();
                control.get('latitude').setErrors({requiredCoordinate: true});
            } else if (!control.get('latitude').value && !control.get('longitude').value) {
                control.get('latitude').setErrors(null);
                control.get('longitude').setErrors(null);
            }
            return null;
        };
    }

    // QA Check - exit gas flow rate and uom must be submitted together
    exitGasFlowUomCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            const flowRate = control.get('exitGasFlowRate').value;
            const uom = control.get('exitGasFlowUomCode').value;

            if ((flowRate && !uom) || (!flowRate && uom)) {
                return {invalidExitGasFlowUomCode: true};
            }
            return null;
        };
    }

    // QA Check - exit velocity and uom must be submitted together
    exitGasVelocityUomCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            const velocity = control.get('exitGasVelocity').value;
            const uom = control.get('exitGasVelocityUomCode').value;

            if ((velocity && !uom) || (!velocity && uom)) {
                return {invalidExitGasVelocityUomCode: true};
            }
            return null;
        };
    }

    // QA Check - Calculated exit gas velocity range
    exitVelocityCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            if (this.releaseType !== this.fugitiveType) {
                const flowRate = control.get('exitGasFlowRate'); // acfs/acfm
                const velocity = control.get('exitGasVelocity'); // fps/fps
                const diameter = control.get('stackDiameter'); // ft
                let calculatedVelocity;
                this.calculatedVelocityUom = 'FPS';
                let minVelocity = 0.001; // fps
                let maxVelocity = 1500; // fps

                if ((diameter !== null && diameter.value > 0)
                    && (flowRate !== null && flowRate.value > 0)) {
                    const computedArea = ((Math.PI) * (Math.pow((diameter.value / 2.0), 2)));

                    if (control.get('exitGasFlowUomCode').value !== null) {
                        calculatedVelocity = (Math.round((flowRate.value / computedArea) * 1000)) / 1000;

                        if ((control.get('exitGasFlowUomCode').value.code !== 'ACFS')) {
                            minVelocity = 0.060; // fpm
                            maxVelocity = 90000; // fpm
                            this.calculatedVelocityUom = 'FPM';
                        }
                    }

                    if (calculatedVelocity > maxVelocity || calculatedVelocity < minVelocity) {
                        this.calculatedVelocity = calculatedVelocity.toString();
                        this.minVelocity = minVelocity;
                        this.maxVelocity = maxVelocity;
                        return {invalidComputedVelocity: true};
                    }
                }
                return null;
            }
            return null;
        };
    }

    // QA Check - Exit gas flow rate or Exit gas velocity must be entered
    exitGasFlowCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            const flowRate = control.get('exitGasFlowRate');
            const velocity = control.get('exitGasVelocity');

            if (control.get('operatingStatusCode').value && control.get('operatingStatusCode').value.code.includes(OperatingStatus.OPERATING)) {
                if (control.get('typeCode').value !== null && control.get('typeCode').value.description !== this.fugitiveType) {
                    if ((flowRate.value === null || flowRate.value === '') && (velocity.value === null || velocity.value === '')) {
                        return {invalidVelocity: true};
                    }
                }
            }
            return null;
        };
    }

    // QA Check - Stack diameter must be less than stack height.
    stackDiameterCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            const diameter = control.get('stackDiameter'); // ft
            const height = control.get('stackHeight'); // ft

            if (this.releaseType !== this.fugitiveType) {
                if ((diameter !== null && height !== null) && (diameter.value > 0 && height.value > 0)) {
                    if (Number(height.value) <= Number(diameter.value)) {
                        this.diameterCheckHeightWarning = 'Warning: Release point Stack Diameter Measure should be less than the release point Stack Height Measure.';
                    } else {
                        this.diameterCheckHeightWarning = null;
                    }
                }
                return null;
            }
        };
    }

    stackDiameterOrStackWidthAndLength(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            const diameter = control.get('stackDiameter'); // ft
            const length = control.get('stackLength');
            const width = control.get('stackWidth');
            if (this.releaseType && this.releaseType !== this.fugitiveType) {
                if ((diameter !== null && !diameter.value) && (!length?.value || !width?.value)) {
                    this.diameterOrLengthAndWidthMessage = 'Stack Diameter or Length/Width must be entered when Release Point Type is a stack.';
                    return {invalidDimensions: true};
                } else if (this.stackDiameter?.value && (this.stackWidth?.value || this.stackLength?.value)) {
                    this.diameterOrLengthAndWidthMessage = 'The release point may have values for Stack Diameter or Length/Width, but not both.';
                    return {invalidDimensions: true};
                } else {
                    this.diameterCheckFlowAndVelWarning = null;
                }
            }
            return null;
        }
    };

    // QA Check - Stack Diameter information is reported, Exit Gas Flow Rate and Velocity should be reported
    stackDiameterCheckForVelAndFlow(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            const diameter = control.get('stackDiameter');
            const exitVelocity = control.get('exitGasVelocity');
            const exitFlowRate = control.get('exitGasFlowRate');

            if (this.releaseType !== this.fugitiveType) {
                if ((diameter !== null && diameter.value !== null && diameter.value !== '') &&
                    ((exitVelocity === null || exitVelocity.value === null || exitVelocity.value === '')
                        || (exitFlowRate === null || exitFlowRate.value === null || exitFlowRate.value === ''))
                ) {
                    this.diameterCheckFlowAndVelWarning = 'Warning: If Release Point Stack Diameter is reported, Exit Gas Flow Rate and Exit Gas Velocity should also be reported.';
                } else {
                    this.diameterCheckFlowAndVelWarning = null;
                }
            }
            return null;
        };
    }

    // QA Check - Exit Gas Flow Rate and Velocity information is reported, Stack Diameter should be reported
    stackVelAndFlowCheckForDiameter(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            const diameter = control.get('stackDiameter');
            const exitVelocity = control.get('exitGasVelocity');
            const exitFlowRate = control.get('exitGasFlowRate');

            if (this.releaseType !== this.fugitiveType) {
                if (((exitVelocity !== null && exitVelocity.value !== null && exitVelocity.value !== '')
                    && (exitFlowRate !== null && exitFlowRate.value !== null && exitFlowRate.value !== ''))
                    && (diameter === null || diameter.value === null || diameter.value === '')) {
                    this.velAndFlowCheckDiameterWarning = 'Warning: If Release Point Exit Gas Flow Rate and Exit Gas Velocity are reported, Stack Diameter should also be reported.';
                } else {
                    this.velAndFlowCheckDiameterWarning = null;
                }
            }
            return null;
        };
    }

    // QA Check - Exit gas flow input must be within +/-5% of computed flow
    exitFlowConsistencyCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            if (this.releaseType !== this.fugitiveType) {
                const diameter = control.get('stackDiameter'); // ft
                const exitVelocity = control.get('exitGasVelocity'); // fps/fpm
                const exitFlowRate = control.get('exitGasFlowRate'); // acfs/acfm
                const velocityUomFPS = "FPS";
                const flowUomACFS = "ACFS";
                const flowUomACFM = "ACFM";
                let valid = true;
                let actualFlowRate;
                this.calculatedFlowRateUom = flowUomACFS;

                if ((diameter !== null && diameter.value > 0)
                    && (exitVelocity !== null && exitVelocity.value > 0)
                    && (exitFlowRate !== null && exitFlowRate.value > 0)) {

                    const computedArea = ((Math.PI) * (Math.pow((diameter.value / 2.0), 2))); // sf
                    const calculatedFlowRate = (computedArea * exitVelocity.value); // cfs/cfm
                    actualFlowRate = exitFlowRate.value;

                    if ((control.get('exitGasVelocityUomCode').value !== null && control.get('exitGasVelocityUomCode').value !== '')
                        && (control.get('exitGasVelocityUomCode').value.code !== velocityUomFPS)) {
                        this.calculatedFlowRateUom = flowUomACFM;
                    }

                    // set actual flow rate UoM to compare to computed flow rate
                    if (control.get('exitGasFlowUomCode').value !== null && control.get('exitGasFlowUomCode').value !== '') {
                        if (control.get('exitGasFlowUomCode').value.code !== flowUomACFS && this.calculatedFlowRateUom === flowUomACFS) {
                            actualFlowRate = exitFlowRate.value / 60; // acfm to acfs
                        } else if (control.get('exitGasFlowUomCode').value.code !== flowUomACFM && this.calculatedFlowRateUom === flowUomACFM) {
                            actualFlowRate = exitFlowRate.value * 60; // acfs to acfm
                        }
                    }

                    // Compare to value with 0.00000001 precision
                    const upperLimit = (Math.round((calculatedFlowRate * 1.05) * 100000000)) / 100000000; // cfs
                    const lowerLimit = (Math.round((calculatedFlowRate * 0.95) * 100000000)) / 100000000; // cfs
                    actualFlowRate = (Math.round(actualFlowRate * 100000000)) / 100000000; // acfs

                    if (actualFlowRate > upperLimit || actualFlowRate < lowerLimit) {
                        valid = false;

                        // If user enters 0.00000001 and calculated flow is less than 0.000000001
                        // Min allowable actual flow rate user can enter is 0.000000001
                        if ((actualFlowRate === 0.00000001 && upperLimit < 0.00000001)) {
                            valid = true;
                        }
                        this.calculatedFlowRate = ((Math.round(calculatedFlowRate * 100000000)) / 100000000).toString();
                        return valid ? null : {invalidFlowRate: true};
                    }
                }
                return null;
            }
            return null;
        };
    }

    // QA Check - check if coordinate is within facility coordinate tolerance
    coordinateToleranceCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            const DEFAULT_TOLERANCE = 0.003;
            const rpLong = control.get('longitude');
            const rpLat = control.get('latitude');
            let longLowerLimit;
            let longUpperLimit;
            let latLowerLimit;
            let latUpperLimit;

            this.lookupService.retrieveLatLongTolerance(this.eisProgramId)
                .subscribe(result => {
                    this.coordinateTolerance = result;

                    setTimeout(() => {
                        if (this.coordinateTolerance) {
                            this.tolerance = this.coordinateTolerance.coordinateTolerance;
                        } else {
                            this.tolerance = DEFAULT_TOLERANCE;
                        }

                        if (rpLong !== null && rpLong.value !== null && rpLong.value !== '') {
                            longUpperLimit = (Math.round((this.facilitySite.longitude + this.tolerance) * 1000000) / 1000000);
                            longLowerLimit = (Math.round((this.facilitySite.longitude - this.tolerance) * 1000000) / 1000000);

                            if (((rpLong.value > longUpperLimit) || (rpLong.value < longLowerLimit))) {
                                control.get('longitude').markAsTouched();
                                control.get('longitude').setErrors({invalidLongitude: true});
                            }
                        }

                        if (rpLat !== null && rpLat.value !== null && rpLat.value !== '') {
                            latUpperLimit = (Math.round((this.facilitySite.latitude + this.tolerance) * 1000000) / 1000000);
                            latLowerLimit = (Math.round((this.facilitySite.latitude - this.tolerance) * 1000000) / 1000000);

                            if (((rpLat.value > latUpperLimit) || (rpLat.value < latLowerLimit))) {
                                control.get('latitude').markAsTouched();
                                control.get('latitude').setErrors({invalidLatitude: true});
                            }
                        }
                    }, 1000);
                });
            return null;
        };
    }

    // QA Check - operating status check vs facility site operating status
    facilitySiteStatusCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {

            const controlStatus = control.get('operatingStatusCode').value;

            if (this.facilityOpCode && controlStatus) {
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

    // QA Check - identifier must be unique
    releasePointIdentifierCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            if (this.releasePointIdentifiers && control.get('releasePointIdentifier').value !== null) {
                if (control.get('releasePointIdentifier').value.trim() === '') {
                    control.get('releasePointIdentifier').setErrors({required: true});
                } else if (this.releasePointIdentifiers.includes(control.get('releasePointIdentifier').value.trim())) {
                    return {duplicateReleasePointIdentifier: true};
                }
            }
            return null;
        };
    }

    requiredIfOperating() {
        return (formControl => {
            if (!formControl.parent) {
                return null;
            }
            if (this.releasePointForm.get('operatingStatusCode').value
                && this.releasePointForm.get('operatingStatusCode').value.code.includes(OperatingStatus.OPERATING)) {
                return Validators.required(formControl);
            }
            return null;
        });
    }
}
