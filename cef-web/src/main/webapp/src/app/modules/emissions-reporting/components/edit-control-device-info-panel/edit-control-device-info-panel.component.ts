import { Component, Input, OnInit, OnChanges } from "@angular/core";
import { Control } from 'src/app/shared/models/control';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FormBuilder, Validators, ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { ControlService } from 'src/app/core/services/control.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-control-device-info-panel',
  templateUrl: './edit-control-device-info-panel.component.html',
  styleUrls: ['./edit-control-device-info-panel.component.scss']
})

export class EditControlDeviceInfoPanelComponent implements OnInit, OnChanges {
  @Input() control: Control;
  controlIdentifiers: String[]=[];

  controlDeviceForm = this.fb.group({
    identifier: ['', Validators.required],
    percentCapture: ['', [
      Validators.max(100.0),
      Validators.min(0),
      Validators.pattern('^[0-9]{1,3}([\.][0-9]{1})?$')
    ]],
    percentControl: ['', [
      Validators.max(100.0),
      Validators.min(0),
      Validators.pattern('^[0-9]{1,3}([\.][0-9]{1})?$')
    ]],
    operatingStatusCode: [null, Validators.required],
    controlMeasureCode: [null, Validators.required],
    description: ['', [
      Validators.required,
      Validators.maxLength(200)
    ]],
    comments: ['', Validators.maxLength(400)]
  }, { validators: [
    this.controlIdentifierCheck()
  ]});

  operatingStatusValues: BaseCodeLookup[];
  controlMeasureCode: BaseCodeLookup[];

  constructor(private fb: FormBuilder,
              private lookupService: LookupService,
              public formUtils: FormUtilsService,
              private controlService: ControlService,
              private route: ActivatedRoute
              ) { }

  ngOnInit() {

    this.lookupService.retrieveOperatingStatus()
      .subscribe(result => {
        this.operatingStatusValues = result;
      });

    this.lookupService.retrieveControlMeasureCodes()
      .subscribe(result => {
        this.controlMeasureCode = result;
      });

    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
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
        if (this.controlIdentifiers.includes(control.get('identifier').value)) {
          return { duplicateControlIdentifier: true };
        }
      }
      return null;
    }
  }

}
