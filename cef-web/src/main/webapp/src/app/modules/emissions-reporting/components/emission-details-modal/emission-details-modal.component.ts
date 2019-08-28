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
import { CalculationMethodCode } from 'src/app/shared/models/calculation-method-code';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';

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
    emissionsNumeratorUom: [null, Validators.required],
    emissionsDenominatorUom: [null, Validators.required],
    emissionsCalcMethodCode: ['', Validators.required],
    totalEmissions: ['', [Validators.required, numberValidator()]],
    emissionsUomCode: [null, Validators.required],
    comments: ['', Validators.maxLength(200)],
  });

  methodValues: CalculationMethodCode[];
  pollutantValues: Pollutant[];
  uomValues: UnitMeasureCode[];
  numeratorUomValues: UnitMeasureCode[];
  denominatorUomValues: UnitMeasureCode[];

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
      this.numeratorUomValues = this.uomValues.filter(val => val.efNumerator);
      this.denominatorUomValues = this.uomValues.filter(val => val.efDenominator);
    });

    if (this.createMode) {
      this.emissionForm.enable();
    } else {
      this.emissionForm.reset(this.emission);
      this.emissionForm.disable();
    }

    this.emissionForm.controls.emissionsCalcMethodCode.valueChanges
    .subscribe(value => {
      if (value && value.totalDirectEntry) {
        this.emissionForm.get('emissionsFactor').disable();
        this.emissionForm.get('emissionsFactorText').disable();
        this.emissionForm.get('emissionsNumeratorUom').disable();
        this.emissionForm.get('emissionsDenominatorUom').disable();
        this.emissionForm.get('emissionsFactor').reset();
        this.emissionForm.get('emissionsFactorText').reset();
        this.emissionForm.get('emissionsNumeratorUom').reset();
        this.emissionForm.get('emissionsDenominatorUom').reset();
      } else {
        this.emissionForm.get('emissionsFactor').enable();
        this.emissionForm.get('emissionsFactorText').enable();
        this.emissionForm.get('emissionsNumeratorUom').enable();
        this.emissionForm.get('emissionsDenominatorUom').enable();
      }
    });

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
      this.emissionForm.markAllAsTouched();
    } else {

      const saveEmission = new Emission();
      Object.assign(saveEmission, this.emissionForm.value);

      if (this.createMode) {

        saveEmission.reportingPeriodId = this.reportingPeriod.id;

        this.emissionService.create(saveEmission)
        .subscribe(result => {

          // Object.assign(this.emission, result);
          this.activeModal.close(result);
        });
      } else {

        saveEmission.id = this.emission.id;

        this.emissionService.update(saveEmission)
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
