import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { OperatingDetail } from 'src/app/shared/models/operating-detail';

@Component({
  selector: 'app-edit-process-operating-detail-panel',
  templateUrl: './edit-process-operating-detail-panel.component.html',
  styleUrls: ['./edit-process-operating-detail-panel.component.scss']
})
export class EditProcessOperatingDetailPanelComponent implements OnInit {
  operatingDetailsForm = this.fb.group({
    actualHoursPerPeriod: [''],
    avgHoursPerDay: [''],
    avgDaysPerWeek: [''],
    avgWeeksPerPeriod: [''],
    percentWinter: [''],
    percentSpring: [''],
    percentSummer: [''],
    percentFall: ['']
  });

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
  }

  onSubmit() {

    console.log(this.operatingDetailsForm);

    let operatingDetails = new OperatingDetail();
    Object.assign(operatingDetails, this.operatingDetailsForm.value);
    console.log(operatingDetails);
  }

}
