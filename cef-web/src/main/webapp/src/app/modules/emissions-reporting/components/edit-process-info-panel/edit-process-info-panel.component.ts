import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormBuilder, Validators, ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Process } from 'src/app/shared/models/process';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SccSearchModalComponent } from 'src/app/modules/emissions-reporting/components/scc-search-modal/scc-search-modal.component';
import { SccCode } from 'src/app/shared/models/scc-code';
import { AircraftEngineTypeCode } from 'src/app/shared/models/aircraft-engine-type-code';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { ActivatedRoute } from '@angular/router';
import { EmissionUnitService } from 'src/app/core/services/emission-unit.service';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';

@Component({
  selector: 'app-edit-process-info-panel',
  templateUrl: './edit-process-info-panel.component.html',
  styleUrls: ['./edit-process-info-panel.component.scss']
})
export class EditProcessInfoPanelComponent implements OnInit, OnChanges {
  @Input() process: Process;
  @Input() unitIdentifier: string;
  @Input() emissionsUnit: EmissionUnit;
  emissionUnit: EmissionUnit;
  emissionsReportYear: number;
  sccRetirementYear: number;
  sccWarning: string;
  aircraftSCCcheck = false;

  processForm = this.fb.group({
    aircraftEngineTypeCode: [null],
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
    comments: ['', Validators.maxLength(400)]
  }, { validators: [
    this.checkPointSourceSccCode(),
    this.checkProcessIdentifier()]
  });

  operatingStatusValues: BaseCodeLookup[];
  aircraftEngineTypeValue: AircraftEngineTypeCode[];
  aircraftEngineSCC: string[];

  constructor(
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    private emissionUnitService: EmissionUnitService,
    private route: ActivatedRoute,
    private modalService: NgbModal,
    private fb: FormBuilder) { }

  ngOnInit() {

    this.lookupService.retrieveOperatingStatus()
    .subscribe(result => {
      this.operatingStatusValues = result;
    });

    this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
      this.emissionsReportYear = data.facilitySite.emissionsReport.year;
    });

    // SCC codes associated with Aircraft Engine Type Codes
    this.aircraftEngineSCC = [
      '2275001000', '2275020000', '2275050011', '2275050012', '2275060011', '2275060012'
    ];

    this.checkAircraftSCC();
  }

  ngOnChanges() {

    this.processForm.reset(this.process);

    if (this.emissionsUnit != null) {
      this.emissionUnitService.retrieve(this.emissionsUnit.id)
      .subscribe(unit => {
        this.emissionUnit = unit;
        this.unitIdentifier = unit.unitIdentifier;
      });
    }
  }

  onChange(newValue) {
    if(newValue) {
      this.processForm.controls.statusYear.reset();
    }
  }

  openSccSearchModal() {
    const modalRef = this.modalService.open(SccSearchModalComponent, { size: 'xl', backdrop: 'static', scrollable: true });

    // update form when modal closes successfully
    modalRef.result.then((modalScc: SccCode) => {
      if (modalScc) {
        this.processForm.get('sccCode').setValue(modalScc.code);
        this.processForm.get('sccDescription').setValue(modalScc.description);

        this.checkAircraftSCC();
      }
    }, () => {
      // needed for dismissing without errors
    });
  }

  checkAircraftSCC() {
    const formSccCode = this.processForm.get('sccCode');
    if (formSccCode.value !== null) {
      for (let scc of this.aircraftEngineSCC) {
        if (scc === formSccCode.value) {

          this.aircraftSCCcheck = true;

          // form field is required if selected SCC is aircraft
          this.processForm.controls.aircraftEngineTypeCode.setValidators([Validators.required]);

          this.lookupService.retrieveAircraftEngineCodes()
          .subscribe(result => {
            this.aircraftEngineTypeValue = result.filter(val => (val.scc === this.processForm.get('sccCode').value));

            for (let item of this.aircraftEngineTypeValue) {
              if (this.process.aircraftEngineTypeCode !== null && (item.code === this.process.aircraftEngineTypeCode.code)) {
                this.processForm.controls['aircraftEngineTypeCode'].setValue(item);
                break
              }
            }
          });
          break;
        }
      }
      this.aircraftSCCcheck = false;
      }

    if (!this.aircraftSCCcheck) {
      // reset form field if selected SCC is not aircraft
      this.processForm.controls.aircraftEngineTypeCode.setValidators(null);
      this.processForm.controls.aircraftEngineTypeCode.reset();
    }
  }

  onSubmit() {

    // console.log(this.processForm);

    // let process = new Process();
    // Object.assign(process, this.processForm.value);
    // console.log(process);
  }

  checkPointSourceSccCode(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      let isValidScc;

      if (control.get('sccCode') !== null && control.get('sccCode').value !== null && control.get('sccCode').value !== '') {

        this.lookupService.retrievePointSourceSccCode(control.get('sccCode').value)
        .subscribe(result => {
          isValidScc = result;

          if (isValidScc !== null) {
            if (isValidScc.lastInventoryYear !== null && (isValidScc.lastInventoryYear >= this.emissionsReportYear)) {
              this.sccRetirementYear = isValidScc.lastInventoryYear;
              this.sccWarning = 'Warning: ' + control.get('sccCode').value + ' has a retirement date of ' + this.sccRetirementYear + '. If applicable, you may want to add a more recent code.';
            } else if (isValidScc.lastInventoryYear !== null && (isValidScc.lastInventoryYear < this.emissionsReportYear)) {
              this.sccRetirementYear = isValidScc.lastInventoryYear;
              control.get('sccCode').markAsTouched();
              control.get('sccCode').setErrors({'sccCodeRetired': true});
              this.sccWarning = null;
            } else if (isValidScc.lastInventoryYear === null) {
              this.sccWarning = null;
            }
          } else if (result === null) {
            control.get('sccCode').markAsTouched();
            control.get('sccCode').setErrors({'sccCodeInvalid': true});
            this.sccWarning = null;
          } else {
            this.sccWarning = null;
          }
        });
      }
      return null;
    }
  }

  // check for duplicate process identifier
  checkProcessIdentifier(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {

      if (control.get('emissionsProcessIdentifier') !== null && this.emissionUnit != null) {
        this.emissionUnit['emissionsProcesses'].forEach(pIdentifier => {
          if (this.process !== undefined) {
            if ((pIdentifier['emissionsProcessIdentifier'] !== this.process.emissionsProcessIdentifier)
            && (pIdentifier['emissionsProcessIdentifier'] === control.get('emissionsProcessIdentifier').value)) {
              control.get('emissionsProcessIdentifier').setErrors({invalidDuplicateProcessIdetifier: true});
            }
          } else if (this.process === undefined)  {
            if (pIdentifier['emissionsProcessIdentifier'] === control.get('emissionsProcessIdentifier').value) {
              control.get('emissionsProcessIdentifier').setErrors({invalidDuplicateProcessIdetifier: true});
            }
          }
          return null;
        });
      }
      return null;
    };
  }
}
