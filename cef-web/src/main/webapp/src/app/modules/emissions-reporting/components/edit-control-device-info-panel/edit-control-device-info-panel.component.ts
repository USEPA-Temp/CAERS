import { Component, Input, OnInit, OnChanges } from "@angular/core";
import { Control } from 'src/app/shared/models/control';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FormBuilder, Validators, ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { ControlService } from 'src/app/core/services/control.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ActivatedRoute } from '@angular/router';
import { InventoryYearCodeLookup } from 'src/app/shared/models/inventory-year-code-lookup';
import { legacyItemValidator } from 'src/app/modules/shared/directives/legacy-item-validator.directive';

@Component({
  selector: 'app-edit-control-device-info-panel',
  templateUrl: './edit-control-device-info-panel.component.html',
  styleUrls: ['./edit-control-device-info-panel.component.scss']
})

export class EditControlDeviceInfoPanelComponent implements OnInit, OnChanges {
  @Input() control: Control;
  @Input() year: number;
  controlIdentifiers: string[] = [];
  facilityOpCode: BaseCodeLookup;

  controlDeviceForm = this.fb.group({
    identifier: ['', Validators.required],
    percentCapture: ['', [
      Validators.max(100.0),
      Validators.min(5),
      Validators.pattern('^[0-9]{1,3}([\.][0-9]{1})?$')
    ]],
    percentControl: ['', [
      Validators.max(100.0),
      Validators.min(1),
      Validators.pattern('^[0-9]{1,3}([\.][0-9]{1,3})?$')
    ]],
    operatingStatusCode: [null, Validators.required],
    controlMeasureCode: [null, [Validators.required]],
    description: ['', [
      Validators.required,
      Validators.maxLength(200)
    ]],
    comments: [null, Validators.maxLength(400)]
  }, { validators: [
    this.controlIdentifierCheck(),
    this.facilitySiteStatusCheck()
  ]});

  subFacilityOperatingStatusValues: BaseCodeLookup[];
  controlMeasureCode: InventoryYearCodeLookup[];

  constructor(private fb: FormBuilder,
              private lookupService: LookupService,
              public formUtils: FormUtilsService,
              private controlService: ControlService,
              private route: ActivatedRoute
              ) { }

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
  }

  ngOnChanges() {

    this.controlDeviceForm.reset(this.control);
  }

  controlIdentifierCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      if (this.controlIdentifiers) {
        if (!control.get('identifier').value || control.get('identifier').value.trim() === '') {
          control.get('identifier').setErrors({required: true});
        } else if (this.controlIdentifiers.includes(control.get('identifier').value.trim())) {
          return { duplicateControlIdentifier: true };
        }
      }
      return null;
    }
  }

  facilitySiteStatusCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const status_perm_shutdown = "PS";
      const status_temp_shutdown = "TS";
      const control_status = control.get('operatingStatusCode').value;

      if (this.facilityOpCode && control_status) {
        if (this.facilityOpCode.code === status_temp_shutdown
          && control_status.code !== status_perm_shutdown
          && control_status.code !== status_temp_shutdown) {
            return {invalidStatusCodeTS: true};
          } else if (this.facilityOpCode.code === status_perm_shutdown
          && control_status.code !== status_perm_shutdown) {
            return {invalidStatusCodePS: true};
          }
      }
      return null;
    }
  }

}
