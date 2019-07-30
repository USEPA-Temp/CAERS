import { Component, OnInit } from '@angular/core';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormBuilder } from '@angular/forms';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Process } from 'src/app/shared/models/process';

@Component({
  selector: 'app-edit-process-info-panel',
  templateUrl: './edit-process-info-panel.component.html',
  styleUrls: ['./edit-process-info-panel.component.scss']
})
export class EditProcessInfoPanelComponent implements OnInit {
  processForm = this.fb.group({
    aircraftEngineTypeCodeCode: [''],
    operatingStatusCode: [],
    emissionsProcessIdentifier: [''],
    statusYear: [''],
    sccCode: [''],
    description: [''],
    comments: ['']
  });

  operatingStatusValues: BaseCodeLookup[];

  constructor(
    private lookupService: LookupService,
    private fb: FormBuilder) { }

  ngOnInit() {

    this.lookupService.retrieveOperatingStatus()
    .subscribe(result => {
      this.operatingStatusValues = result;
    });
  }

  onSubmit() {

    console.log(this.processForm);

    let process = new Process();
    Object.assign(process, this.processForm.value);
    console.log(process);
  }

}
