import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { OperatingDetail } from 'src/app/shared/models/operating-detail';
import { wholeNumberValidator } from 'src/app/modules/shared/directives/whole-number-validator.directive';

@Component({
  selector: 'app-edit-process-operating-detail-panel',
  templateUrl: './edit-process-operating-detail-panel.component.html',
  styleUrls: ['./edit-process-operating-detail-panel.component.scss']
})
export class EditProcessOperatingDetailPanelComponent implements OnInit, OnChanges {

  readonly numberPattern = '^[0-9]*\\.?[0-9]+$';

  @Input() operatingDetails: OperatingDetail;
  operatingDetailsForm = this.fb.group({
    actualHoursPerPeriod: ['', [
      Validators.required,
      Validators.min(1),
      Validators.max(8784),
      wholeNumberValidator(),
    ]],
    avgHoursPerDay: ['', [
      Validators.required,
      Validators.min(0.1),
      Validators.max(24),
      Validators.pattern(this.numberPattern)
    ]],
    avgDaysPerWeek: ['', [
      Validators.required,
      Validators.min(0.1),
      Validators.max(7),
      Validators.pattern(this.numberPattern)
    ]],
    avgWeeksPerPeriod: ['', [
      Validators.required,
      Validators.min(1),
      Validators.max(52),
      wholeNumberValidator()
    ]],
    percentWinter: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(100),
      Validators.pattern(this.numberPattern)
    ]],
    percentSpring: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(100),
      Validators.pattern(this.numberPattern)
    ]],
    percentSummer: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(100),
      Validators.pattern(this.numberPattern)
    ]],
    percentFall: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(100),
      Validators.pattern(this.numberPattern)
    ]]
  });

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
  }

    validateOperatingPercent() {
    if ((parseFloat(this.operatingDetailsForm.controls.percentFall.value) +
        parseFloat(this.operatingDetailsForm.controls.percentSpring.value) +
        parseFloat(this.operatingDetailsForm.controls.percentWinter.value) +
        parseFloat(this.operatingDetailsForm.controls.percentSummer.value) > 100.5) || (
        parseFloat(this.operatingDetailsForm.controls.percentFall.value) +
        parseFloat(this.operatingDetailsForm.controls.percentSpring.value) +
        parseFloat(this.operatingDetailsForm.controls.percentWinter.value) +
        parseFloat(this.operatingDetailsForm.controls.percentSummer.value) < 99.5)) {
          return false;
        } else {
          return true;
        }
  }

  ngOnChanges() {

    this.operatingDetailsForm.reset(this.operatingDetails);
  }

  onSubmit() {

    // console.log(this.operatingDetailsForm);

    // let operatingDetails = new OperatingDetail();
    // Object.assign(operatingDetails, this.operatingDetailsForm.value);
    // console.log(operatingDetails);
  }

}
