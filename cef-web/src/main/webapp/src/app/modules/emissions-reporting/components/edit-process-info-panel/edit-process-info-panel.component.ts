import { Component, OnInit, Input, OnChanges,AfterContentChecked } from '@angular/core';
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
import { OperatingStatus } from 'src/app/shared/enums/operating-status';

@Component({
  selector: 'app-edit-process-info-panel',
  templateUrl: './edit-process-info-panel.component.html',
  styleUrls: ['./edit-process-info-panel.component.scss']
})

export class EditProcessInfoPanelComponent implements OnInit, OnChanges, AfterContentChecked {
  @Input() process: Process;
  @Input() unitIdentifier: string;
  @Input() emissionsUnit: EmissionUnit;
  sccAndAircraftCombinations: string[] = [];
  emissionsProcessIdentifiers: string[] = [];
  emissionUnit: EmissionUnit;
  emissionsReportYear: number;
  sccRetirementYear: number;
  sccWarning: string;
  aircraftSCCcheck = false;
  invalidAircraftSCC = false;
  processHasAETC = false;
  facilityOpCode: BaseCodeLookup;

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
      this.requiredIfOperating(),
      Validators.maxLength(500)
    ]],
    description: ['', [
      this.requiredIfOperating(),
      Validators.maxLength(200)
    ]],
    comments: ['', Validators.maxLength(400)]
  }, { validators: [
    this.checkPointSourceSccCode(),
    this.checkProcessIdentifier(),
    this.legacyAetcValidator(),
    this.checkMatchSccAircraft(),
    this.checkSccAndAircraftDuplicate(),
    this.facilitySiteStatusCheck(),
    this.statusYearRequiredCheck()]
  });

  operatingSubFacilityStatusValues: BaseCodeLookup[];
  aircraftEngineTypeValue: AircraftEngineTypeCode[];
  aircraftEngineSCC: string[];

  constructor(
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    private emissionUnitService: EmissionUnitService,
    private route: ActivatedRoute,
    private modalService: NgbModal,
    private fb: FormBuilder) {}

  ngOnInit() {
    this.lookupService.retrieveSubFacilityOperatingStatus()
    .subscribe(result => {
      this.operatingSubFacilityStatusValues = result;
    });

    this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
      this.facilityOpCode = data.facilitySite.operatingStatusCode;
      this.emissionsReportYear = data.facilitySite.emissionsReport.year;
    });

    // SCC codes associated with Aircraft Engine Type Codes
    this.aircraftEngineSCC = [
      '2275001000', '2275020000', '2275050011', '2275050012', '2275060011', '2275060012'
    ];

    this.checkAircraftSCC();
  }

  ngAfterContentChecked() {
    if(this.emissionsUnit && this.emissionsUnit.emissionsProcesses){
      this.emissionsUnit.emissionsProcesses.forEach(process => {
        this.emissionsProcessIdentifiers.push(process.emissionsProcessIdentifier);
        if(process['aircraftEngineTypeCode'] && process['sccCode']){
          // if a process is selected to edit then check to make sure its id isnt equal to the id of the process we are looping through
          // to avoid comparing its own combination to itself, if its a new process then skip this check
          if ((!this.process) || (this.process && process['id']!== this.process.id)){
            const combination = process['aircraftEngineTypeCode'].code + process['sccCode'];
            this.sccAndAircraftCombinations.push(combination);
          }
        }
        if (this.process) {
          this.emissionsProcessIdentifiers = this.emissionsProcessIdentifiers.filter(identifer => identifer.toString() !== this.process.emissionsProcessIdentifier);
        }
      })
    }
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
    if (newValue) {
      this.processForm.controls.statusYear.reset();
    }
    this.processForm.controls.description.updateValueAndValidity();
    this.processForm.controls.sccDescription.updateValueAndValidity();
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

  // check for aircraft type SCC and associated Aircraft Engine Type Codes
  checkAircraftSCC() {
    this.aircraftSCCcheck = false;
    this.invalidAircraftSCC = false;
    this.checkForAircraftSCC();
    if (this.aircraftSCCcheck) {
      // get AETC list and set form value
      this.getAircraftEngineCodes();
    } else if (!this.aircraftSCCcheck && this.process !== undefined && this.process.aircraftEngineTypeCode !== null) {
      this.aircraftEngineTypeValue = null;
      this.processForm.controls.aircraftEngineTypeCode.setValidators(null);
      this.processForm.controls.aircraftEngineTypeCode.updateValueAndValidity();
      this.processHasAETC = true;
      this.invalidAircraftSCC = true;
    }
  }

  // check if aircraft type SCC
  checkForAircraftSCC() {
    const formSccCode = this.processForm.get('sccCode');
    this.aircraftSCCcheck = false;
    for (const scc of this.aircraftEngineSCC) {
      if (scc === formSccCode.value) {
        this.aircraftSCCcheck = true;
        this.processHasAETC = true;
        break;
      }
    }

    if (this.aircraftSCCcheck) {
      this.processForm.controls.aircraftEngineTypeCode.setValidators([Validators.required]);
      this.processForm.controls.aircraftEngineTypeCode.updateValueAndValidity();
    } else if (!this.aircraftSCCcheck) {
      this.processForm.controls.aircraftEngineTypeCode.setValidators(null);
      this.processForm.controls.aircraftEngineTypeCode.updateValueAndValidity();
      if (this.process !== undefined && this.process.aircraftEngineTypeCode !== null) {
        this.processForm.controls.aircraftEngineTypeCode.reset(this.process.aircraftEngineTypeCode);
        this.invalidAircraftSCC = true;
        this.processHasAETC = true;
      } else {
        this.aircraftEngineTypeValue = null;
        this.processHasAETC = false;
      }
    }
  }

  // get AETC list
  getAircraftEngineCodes() {
    let codeInList = false;
    this.lookupService.retrieveCurrentAircraftEngineCodes(this.processForm.get('sccCode').value, this.emissionsReportYear)
    .subscribe(result => {
      this.aircraftEngineTypeValue = result;

      // check if process AETC is valid
      if (this.aircraftSCCcheck && this.aircraftEngineTypeValue !== null && this.aircraftEngineTypeValue !== undefined) {
        if (this.process !== undefined && this.process.aircraftEngineTypeCode !== null) {
          for (const item of this.aircraftEngineTypeValue) {

            if (item.code === this.process.aircraftEngineTypeCode.code) {
              this.invalidAircraftSCC = false;
              codeInList = true;
              this.processForm.controls.aircraftEngineTypeCode.setValue(item);
              break;
            }
          }
        }
        if (!codeInList) {
          this.processForm.controls.aircraftEngineTypeCode.setValue(null);
          this.processForm.controls.aircraftEngineTypeCode.setValidators([Validators.required]);
          this.processForm.controls.aircraftEngineTypeCode.updateValueAndValidity();
        }
      }
    });
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
              this.sccWarning = 'Warning: ' + control.get('sccCode').value + ' has a retirement date of ' + this.sccRetirementYear
                  + '. If applicable, you may want to add a more recent code.';
            } else if (isValidScc.lastInventoryYear !== null && (isValidScc.lastInventoryYear < this.emissionsReportYear)) {
              this.sccRetirementYear = isValidScc.lastInventoryYear;
              control.get('sccCode').markAsTouched();
              control.get('sccCode').setErrors({sccCodeRetired: true});
              this.sccWarning = null;
            } else if (isValidScc.lastInventoryYear === null) {
              this.sccWarning = null;
            }
          } else if (result === null) {
            control.get('sccCode').markAsTouched();
            control.get('sccCode').setErrors({sccCodeInvalid: true});
            this.sccWarning = null;
          } else {
            this.sccWarning = null;
          }
        });
      }
      return null;
    };
  }

  // check for duplicate process identifier
  checkProcessIdentifier(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      if (this.emissionsProcessIdentifiers && control.get('emissionsProcessIdentifier').value) {
        if (control.get('emissionsProcessIdentifier').value.trim() === '') {
          control.get('emissionsProcessIdentifier').setErrors({required: true});
        } else if (this.emissionsProcessIdentifiers.includes(control.get('emissionsProcessIdentifier').value.trim())) {
          return { invalidDuplicateProcessIdetifier: true };
        }
      }
      return null;
    };
  }

  legacyAetcValidator(): ValidatorFn {
    return (control: FormGroup): {[key: string]: any} | null => {
      // show legacy AETC error message if the process should have an AETC, if there was already an existing one,
      // and if the user hasn't selected a new code
      if (this.processHasAETC && this.process && this.process.aircraftEngineTypeCode
          && this.process.aircraftEngineTypeCode.lastInventoryYear
          && this.process.aircraftEngineTypeCode.lastInventoryYear < this.emissionsReportYear
          && (control.get('aircraftEngineTypeCode') === null || control.get('aircraftEngineTypeCode').value === null)) {
        return {legacyAetc: {value: `${this.process.aircraftEngineTypeCode.faaAircraftType} - ${this.process.aircraftEngineTypeCode.engine}`} };
      }
      return null;
    };
  }


  checkMatchSccAircraft(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      if (this.invalidAircraftSCC) {
        if (control.get('aircraftEngineTypeCode') !== null && control.get('aircraftEngineTypeCode').value !== null) {
          control.get('aircraftEngineTypeCode').setErrors({invalidAircraftSCC: true});
        } else {
          control.get('aircraftEngineTypeCode').setErrors(null);
        }
        return null;
      }
    };
  }

  checkSccAndAircraftDuplicate(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      if ((control.get('aircraftEngineTypeCode').value) && (control.get('sccCode').value)) {
        const codeCombo = control.get('aircraftEngineTypeCode').value.code + control.get('sccCode').value;
        this.sccAndAircraftCombinations.forEach(combination => {
          if (codeCombo === combination) {
            control.get('aircraftEngineTypeCode').setErrors({invalidAircraftSCCCombination: true});
            control.get('sccCode').setErrors({invalidAircraftSCCCombination: true});
          } else {
            control.get('sccCode').setErrors(null);
            control.get('aircraftEngineTypeCode').setErrors(null);
          }
        });
      } else {
        return null;
      }
    };
  }

  facilitySiteStatusCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const controlStatus = control.get('operatingStatusCode').value;

      if (this.facilityOpCode && controlStatus) {
        if (this.facilityOpCode.code === OperatingStatus.TEMP_SHUTDOWN
          && controlStatus.code !== OperatingStatus.PERM_SHUTDOWN
          && controlStatus.code !== OperatingStatus.TEMP_SHUTDOWN) {
            return {invalidStatusCodeTS: true};
          } else if (this.facilityOpCode.code === OperatingStatus.PERM_SHUTDOWN
          && controlStatus.code !== OperatingStatus.PERM_SHUTDOWN) {
            return {invalidStatusCodePS: true};
          }
      }
      return null;
    };
  }

  statusYearRequiredCheck(): ValidatorFn {
    return (control: FormGroup): ValidationErrors | null => {
      const statusYear = control.get('statusYear').value;

      if (statusYear === null || statusYear === '') {
          return {statusYearRequiredFailed: true};
      }
      return null;
    };
  }

  requiredIfOperating() {
    return (formControl => {
      if (!formControl.parent) {
        return null;
      }

      if (this.processForm.get('operatingStatusCode').value
        && this.processForm.get('operatingStatusCode').value.code.includes(OperatingStatus.OPERATING)) {
        return Validators.required(formControl);
      }
      return null;
    });
  }

}
