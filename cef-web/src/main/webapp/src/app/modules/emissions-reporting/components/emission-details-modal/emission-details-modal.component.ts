import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Emission } from 'src/app/shared/models/emission';
import { FormBuilder, Validators } from '@angular/forms';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { Process } from 'src/app/shared/models/process';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Pollutant } from 'src/app/shared/models/pollutant';
import { EmissionService } from 'src/app/core/services/emission.service';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { numberValidator } from 'src/app/modules/shared/directives/number-validator.directive';

@Component({
  selector: 'app-emission-details-modal',
  templateUrl: './emission-details-modal.component.html',
  styleUrls: ['./emission-details-modal.component.scss']
})
export class EmissionDetailsModalComponent implements OnInit {
  @Input() emission: Emission;
  @Input() reportingPeriod: ReportingPeriod;
  @Input() process: Process;
  @Input() editable = false;
  @Input() createMode = false;

  emissionForm = this.fb.group({
    pollutant: [null, Validators.required],
    emissionsFactor: ['', [Validators.required, numberValidator()]],
    emissionsFactorText: ['', [Validators.required, Validators.maxLength(100)]],
    emissionsCalcMethodCode: ['', Validators.required],
    totalEmissions: ['', [Validators.required, numberValidator()]],
    emissionsUomCode: [null, Validators.required],
    comments: ['', Validators.maxLength(200)],
    emissionsNumeratorUom: [null],
    emissionsDenominatorUom: [null]
  });

  methodValues: BaseCodeLookup[];
  pollutantValues: Pollutant[];
  uomValues: BaseCodeLookup[];

  constructor(
    private emissionService: EmissionService,
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    public activeModal: NgbActiveModal,
    private fb: FormBuilder) { }

  ngOnInit() {

    this.lookupService.retrieveCalcMethod()
    .subscribe(result => {
      this.methodValues = result;
    });

    this.lookupService.retrievePollutant()
    .subscribe(result => {
      this.pollutantValues = result;
    });

    this.lookupService.retrieveUom()
    .subscribe(result => {
      this.uomValues = result;
    });

    if (this.createMode) {
      this.emissionForm.enable();
    } else {
      this.emissionForm.reset(this.emission);
      this.emissionForm.disable();
    }

  }

  onCancelEdit() {
    this.emissionForm.enable();
    if (!this.createMode) {
      this.emissionForm.reset(this.emission);
    }
  }

  onClose() {
    this.activeModal.dismiss();
  }

  onEdit() {
    this.emissionForm.enable();
  }

  onSubmit() {

    if (!this.emissionForm.valid) {
      // TODO: update to angular 8 to enable this function for showing all validation messages
      // this.emissionForm.markAllAsTouched();
    } else {
      if (this.createMode) {
        const saveEmission = new Emission();
        Object.assign(saveEmission, this.emissionForm.value);

        saveEmission.reportingPeriodId = this.reportingPeriod.id;

        this.emissionService.create(saveEmission)
        .subscribe(result => {

          // Object.assign(this.emission, result);
          this.activeModal.close(result);
        });
      } else {
        const updateEmission = new Emission();
        Object.assign(updateEmission, this.emissionForm.value);

        updateEmission.id = this.emission.id;

        this.emissionService.update(updateEmission)
        .subscribe(result => {

          Object.assign(this.emission, result);
          this.activeModal.close(result);
        });
      }
    }
  }

  searchPollutants = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => this.pollutantValues.filter(v => v.pollutantName.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || v.pollutantCode.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || (v.pollutantCasId ? v.pollutantCasId.toLowerCase().indexOf(term.toLowerCase()) > -1 : false))
                                        .slice(0, 20))
    )

  pollutantFormatter = (result: Pollutant) => `${result.pollutantName}  -  ${result.pollutantCode} ${result.pollutantCasId ? ' - ' + result.pollutantCasId : ''}`;

}
