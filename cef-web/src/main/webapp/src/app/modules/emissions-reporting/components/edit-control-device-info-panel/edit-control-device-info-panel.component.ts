import {Component, Input, OnInit, OnChanges} from '@angular/core';
import {Control} from 'src/app/shared/models/control';
import {BaseCodeLookup} from 'src/app/shared/models/base-code-lookup';
import {FormBuilder, Validators, ValidatorFn, FormGroup, ValidationErrors} from '@angular/forms';
import {LookupService} from 'src/app/core/services/lookup.service';
import {FormUtilsService} from 'src/app/core/services/form-utils.service';
import {ControlService} from 'src/app/core/services/control.service';
import {FacilitySite} from 'src/app/shared/models/facility-site';
import {ActivatedRoute} from '@angular/router';
import {InventoryYearCodeLookup} from 'src/app/shared/models/inventory-year-code-lookup';
import {legacyItemValidator} from 'src/app/modules/shared/directives/legacy-item-validator.directive';
import {VariableValidationType} from 'src/app/shared/enums/variable-validation-type';
import {OperatingStatus} from 'src/app/shared/enums/operating-status';
import {NgbDate} from '@ng-bootstrap/ng-bootstrap';
import { numberValidator } from 'src/app/modules/shared/directives/number-validator.directive';

@Component({
    selector: 'app-edit-control-device-info-panel',
    templateUrl: './edit-control-device-info-panel.component.html',
    styleUrls: ['./edit-control-device-info-panel.component.scss'],
})

export class EditControlDeviceInfoPanelComponent implements OnInit, OnChanges {
    @Input() control: Control;
    @Input() year: number;
    controlIdentifiers: string[] = [];
    facilityOpCode: BaseCodeLookup;
    facilitySourceTypeCode: BaseCodeLookup;

    controlDeviceForm = this.fb.group({
        identifier: ['', Validators.required],
        percentControl: ['', [
            Validators.max(100.0),
            Validators.min(1),
            Validators.pattern('^[0-9]{1,3}([\.][0-9]{1})?$')
        ]],
        operatingStatusCode: [null, Validators.required],
        statusYear: ['', [
            Validators.required,
            Validators.min(1900),
            Validators.max(2050),
            numberValidator()
        ]],
        controlMeasureCode: [null, [Validators.required]],
        numberOperatingMonths: [null, [
            Validators.max(12.0),
            Validators.min(1),
            Validators.pattern('^[0-9]{1,2}$')]],
        upgradeDescription: [null, [
            Validators.maxLength(200)
        ]],
        startDate: [null],
        upgradeDate: [null],
        endDate: [null],
        description: ['', [
            Validators.required,
            Validators.maxLength(200)
        ]],
        comments: [null, Validators.maxLength(400)]
    }, {
        validators: [
            this.controlIdentifierCheck(),
            this.facilitySiteStatusCheck(),
            this.controlDatesCheck()
        ]
    });

    subFacilityOperatingStatusValues: BaseCodeLookup[];
    controlMeasureCode: InventoryYearCodeLookup[];

    constructor(private fb: FormBuilder,
                private lookupService: LookupService,
                public formUtils: FormUtilsService,
                private controlService: ControlService,
                private route: ActivatedRoute
    ) {
    }

    ngOnInit() {

        this.lookupService.retrieveSubFacilityOperatingStatus()
            .subscribe(result => {
                this.subFacilityOperatingStatusValues = result;
            });

        this.controlDeviceForm.get('controlMeasureCode').setValidators([Validators.required, legacyItemValidator(this.year, 'Control Measure Code', 'description')]);

        this.lookupService.retrieveCurrentControlMeasureCodes(this.year)
            .subscribe(result => {
                this.controlMeasureCode = result;
            });

        this.route.data
            .subscribe((data: { facilitySite: FacilitySite }) => {
                this.facilityOpCode = data.facilitySite.operatingStatusCode;
                this.facilitySourceTypeCode = data.facilitySite.facilitySourceTypeCode;
                this.controlService.retrieveForFacilitySite(data.facilitySite.id)
                    .subscribe(controls => {
                        controls.forEach(c => {
                            this.controlIdentifiers.push(c.identifier);
                        });

                        // if a control is being edited then filter that identifer out the list so the validator check doesnt identify it as a duplicate
                        if (this.control) {
                            this.controlIdentifiers = this.controlIdentifiers.filter(identifer => identifer.toString() !== this.control.identifier);
                        }

                    });
            });

        if (this.control) {
            this.controlDeviceForm.get('startDate').setValue(this.transformDate(this.control.startDate));
            this.controlDeviceForm.get('upgradeDate').setValue(this.transformDate(this.control.upgradeDate));
            this.controlDeviceForm.get('endDate').setValue(this.transformDate(this.control.endDate));
        }

    }

    ngOnChanges() {

        this.controlDeviceForm.reset(this.control);
    }

    onChange(newValue) {
        if (newValue) {
            this.controlDeviceForm.controls.statusYear.reset();
        }
        this.controlDeviceForm.controls.statusYear.markAsTouched();
    }

    transformDate(date) {
        if (date) {
            const existingDate = new Date(date);
            let transformedDate = null;
            date = new Date(existingDate.setMinutes(existingDate.getMinutes() + existingDate.getTimezoneOffset()));

            transformedDate = new NgbDate(date.getFullYear(),
                date.getMonth() + 1,
                date.getDate());

            return transformedDate;
        }

        return null;
    }

    controlIdentifierCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            const controlId: string = control.get('identifier').value;
            if (this.controlIdentifiers) {
                if (!controlId || controlId.trim() === '') {
                    control.get('identifier').setErrors({required: true});
                } else {

                    for (const id of this.controlIdentifiers) {
                        if (id.trim().toLowerCase() === controlId.trim().toLowerCase()) {
                            return {duplicateControlIdentifier: true};
                        }
                    }
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

    controlDatesCheck(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            const start = control.get('startDate').value;
            const upgrade = control.get('upgradeDate').value;
            const end = control.get('endDate').value;
            const maxDateRange = new Date(2050, 12, 31);
            const minDateRange = new Date(1900, 1, 1);

            const startDate = start ? new Date(start.year, start.month - 1, start.day) : null;
            const endDate = end ? new Date(end.year, end.month - 1, end.day) : null;
            const upgradeDate = upgrade ? new Date(upgrade.year, upgrade.month - 1, upgrade.day) : null;

            if (endDate && (endDate > maxDateRange || endDate < minDateRange)) {
                control.get('endDate').markAsTouched();
                control.get('endDate').setErrors({endDateRangeInvalid: true});
            }

            if (startDate && (startDate > maxDateRange || startDate < minDateRange)) {
                control.get('startDate').markAsTouched();
                control.get('startDate').setErrors({startDateRangeInvalid: true});
            } else if (startDate && endDate && startDate > endDate) {
                control.get('startDate').markAsTouched();
                control.get('startDate').setErrors({startDateInvalid: true});
            } else {
                control.get('startDate').setErrors(null);
            }

            if (upgradeDate && (upgradeDate > maxDateRange || upgradeDate < minDateRange)) {
                control.get('upgradeDate').markAsTouched();
                control.get('upgradeDate').setErrors({upgradeDateRangeInvalid: true});
            } else if (upgradeDate && (startDate && startDate > upgradeDate) || (endDate && upgradeDate > endDate)) {
                control.get('upgradeDate').markAsTouched();
                control.get('upgradeDate').setErrors({upgradeDateInvalid: true});
            } else {
                control.get('upgradeDate').setErrors(null);
            }

            return null;
        };
    }

}
