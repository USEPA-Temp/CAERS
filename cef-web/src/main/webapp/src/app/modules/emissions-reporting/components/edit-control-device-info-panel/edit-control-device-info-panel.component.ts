import { Component, Input, OnInit, OnChanges } from "@angular/core";
import { Control } from 'src/app/shared/models/control';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FormBuilder, Validators } from '@angular/forms';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';

@Component({
  selector: 'app-edit-control-device-info-panel',
  templateUrl: './edit-control-device-info-panel.component.html',
  styleUrls: ['./edit-control-device-info-panel.component.scss']
})

export class EditControlDeviceInfoPanelComponent implements OnInit, OnChanges {
  @Input() control: Control;

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
    comments: ['', Validators.maxLength(2000)]
  });

  operatingStatusValues: BaseCodeLookup[];
  controlMeasureCode: BaseCodeLookup[];

  constructor(private fb: FormBuilder,
              private lookupService: LookupService,
              public formUtils: FormUtilsService
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
  }

  ngOnChanges() {

    this.controlDeviceForm.reset(this.control);
  }

}
