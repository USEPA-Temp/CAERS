import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormBuilder, Validators } from '@angular/forms';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Process } from 'src/app/shared/models/process';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SccSearchModalComponent } from 'src/app/modules/emissions-reporting/components/scc-search-modal/scc-search-modal.component';
import { SccCode } from 'src/app/shared/models/scc-code';

@Component({
  selector: 'app-edit-process-info-panel',
  templateUrl: './edit-process-info-panel.component.html',
  styleUrls: ['./edit-process-info-panel.component.scss']
})
export class EditProcessInfoPanelComponent implements OnInit, OnChanges {
  @Input() process: Process;
  processForm = this.fb.group({
    aircraftEngineTypeCodeCode: [''],
    operatingStatusCode: [null, Validators.required],
    emissionsProcessIdentifier: ['', [
      Validators.required,
      Validators.maxLength(20)
    ]],
    statusYear: ['', [
      Validators.required,
      Validators.min(1900),
      Validators.max(2050),
      Validators.pattern('[0-9]*')
    ]],
    sccCode: ['', [
      Validators.required,
      Validators.maxLength(20)
    ]],
    sccDescription: ['', [
      Validators.required,
      Validators.maxLength(500)
    ]],
    description: ['', [
      Validators.required,
      Validators.maxLength(200)
    ]],
    comments: ['', Validators.maxLength(2000)]
  });

  operatingStatusValues: BaseCodeLookup[];

  constructor(
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    private modalService: NgbModal,
    private fb: FormBuilder) { }

  ngOnInit() {

    this.lookupService.retrieveOperatingStatus()
    .subscribe(result => {
      this.operatingStatusValues = result;
    });

  }

  ngOnChanges() {

    this.processForm.reset(this.process);
  }

  openSccSearchModal() {
    const modalRef = this.modalService.open(SccSearchModalComponent, { size: 'xl', backdrop: 'static', scrollable: true });

    // update form when modal closes successfully
    modalRef.result.then((modalScc: SccCode) => {
      if (modalScc) {
        this.processForm.get('sccCode').setValue(modalScc.code);
        this.processForm.get('sccDescription').setValue(modalScc.description);
      }
    }, () => {
      // needed for dismissing without errors
    });
  }

  onSubmit() {

    // console.log(this.processForm);

    // let process = new Process();
    // Object.assign(process, this.processForm.value);
    // console.log(process);
  }

}
