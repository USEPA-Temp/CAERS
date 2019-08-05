import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { OperatingDetail } from 'src/app/shared/models/operating-detail';

@Component({
  selector: 'app-edit-process-operating-detail-panel',
  templateUrl: './edit-process-operating-detail-panel.component.html',
  styleUrls: ['./edit-process-operating-detail-panel.component.scss']
})
export class EditProcessOperatingDetailPanelComponent implements OnInit {
  operatingDetailsForm = this.fb.group({
    actualHoursPerPeriod: ['', [
      Validators.required,
      Validators.min(0),
      Validators.pattern('[0-9]*')
    ]],
    avgHoursPerDay: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(24),
      Validators.pattern('[0-9]*')
    ]],
    avgDaysPerWeek: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(7),
      Validators.pattern('[0-9]*')
    ]],
    avgWeeksPerPeriod: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(52),
      Validators.pattern('[0-9]*')
    ]],
    percentWinter: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(100),
      Validators.pattern('^[0-9]*\\.?[0-9]+$')
    ]],
    percentSpring: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(100),
      Validators.pattern('^[0-9]*\\.?[0-9]+$')
    ]],
    percentSummer: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(100),
      Validators.pattern('^[0-9]*\\.?[0-9]+$')
    ]],
    percentFall: ['', [
      Validators.required,
      Validators.min(0),
      Validators.max(100),
      Validators.pattern('^[0-9]*\\.?[0-9]+$')
    ]]
  });

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
  }

  onSubmit() {

    // console.log(this.operatingDetailsForm);

    // let operatingDetails = new OperatingDetail();
    // Object.assign(operatingDetails, this.operatingDetailsForm.value);
    // console.log(operatingDetails);
  }

}
