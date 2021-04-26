import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { Pollutant } from 'src/app/shared/models/pollutant';
import { ControlPollutant } from 'src/app/shared/models/control-pollutant';
import { LookupService } from 'src/app/core/services/lookup.service';
import { ControlService } from 'src/app/core/services/control.service';
import { Validators, FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { legacyItemValidator } from 'src/app/modules/shared/directives/legacy-item-validator.directive';
import { ControlPathPollutant } from 'src/app/shared/models/control-path-pollutant';
import { ControlPathService } from 'src/app/core/services/control-path.service';

@Component({
  selector: 'app-control-pollutant-modal',
  templateUrl: './control-pollutant-modal.component.html',
  styleUrls: ['./control-pollutant-modal.component.scss']
})
export class ControlPollutantModalComponent implements OnInit {
  controlPollutants: ControlPollutant[];
  controlPathPollutants: ControlPathPollutant[];
  pollutantValues: Pollutant[];
  selectedPollutant: Pollutant;
  selectedControlPollutant: ControlPollutant;
  selectedControlPathPollutant: ControlPathPollutant;
  controlId: number;
  controlPathId: number;
  readonly path = 'CONTROL_PATH';
  readonly device = 'CONTROL_DEVICE';
  facilitySiteId: number;
  year: number;
  edit: boolean;
  duplicateCheck = true;
  controlPollutantFor: string;

  pollutantForm = this.fb.group({
      pollutant: [null , [Validators.required]],
      percentReduction: ['', [
        Validators.required,
        Validators.max(99.9),
        Validators.min(5),
        Validators.pattern('^[0-9]{1,3}([\.][0-9]{1})?$')
      ]]
  });

  constructor(private lookupService: LookupService,
              private fb: FormBuilder,
              public activeModal: NgbActiveModal,
              private controlService: ControlService,
              private controlPathService: ControlPathService,
              private toastr: ToastrService) { }

  ngOnInit() {
    if (this.controlPollutantFor === this.device) {
      if (this.selectedControlPollutant) {
        this.pollutantForm.reset(this.selectedControlPollutant);
      } else {
        this.selectedControlPollutant = new ControlPollutant();
      }
    } else if (this.controlPollutantFor === this.path) {
        if (this.selectedControlPathPollutant) {
        this.pollutantForm.reset(this.selectedControlPathPollutant);
      } else {
        this.selectedControlPathPollutant = new ControlPathPollutant();
      }
    }

    this.pollutantForm.get('pollutant').setValidators([Validators.required, legacyItemValidator(this.year, 'Pollutant', 'pollutantName')]);

    this.lookupService.retrieveCurrentPollutants(this.year)
    .subscribe(result => {
      this.pollutantValues = result;
    });
  }

  isValid() {
    return this.pollutantForm.valid;
  }

  onClose() {
    this.activeModal.close('dontUpdate');
  }
  onSubmit() {
    if (!this.isValid()) {
        this.pollutantForm.markAllAsTouched();
    } else {

      if (this.controlPollutantFor === this.device) {
        if (!this.edit){
          this.controlPollutants.forEach(pollutant => {
            if (pollutant.pollutant.pollutantName === this.pollutantForm.get('pollutant').value.pollutantName) {
              this.duplicateCheck = false;
              this.toastr.error('', 'This Control Path already contains this Control Pollutant, duplicates are not allowed.');
            }
          });
        }

        if (this.duplicateCheck) {
          this.selectedPollutant = this.pollutantForm.get('pollutant').value;
          this.selectedControlPollutant.pollutant = this.selectedPollutant;
          this.selectedControlPollutant.percentReduction = this.pollutantForm.get('percentReduction').value;
          this.selectedControlPollutant.controlId = this.controlId;
          this.selectedControlPollutant.facilitySiteId = this.facilitySiteId;

          if (!this.edit) {
            this.controlService.createPollutant(this.selectedControlPollutant).subscribe(() => {
              this.activeModal.close();
            });
          } else {
            this.controlService.updatePollutant(this.selectedControlPollutant).subscribe(() => {
              this.activeModal.close();
            });
          }
        }
      } else if (this.controlPollutantFor === this.path) {
        if (!this.edit) {
          this.controlPathPollutants.forEach(pollutant => {
            if (pollutant.pollutant.pollutantName === this.pollutantForm.get('pollutant').value.pollutantName) {
              this.duplicateCheck = false;
              this.toastr.error('', 'This Control Path already contains this Control Path Pollutant, duplicates are not allowed.');
            }
          });
        }

        if (this.duplicateCheck) {
          this.selectedPollutant = this.pollutantForm.get('pollutant').value;
          this.selectedControlPathPollutant.pollutant = this.selectedPollutant;
          this.selectedControlPathPollutant.percentReduction = this.pollutantForm.get('percentReduction').value;
          this.selectedControlPathPollutant.controlPathId = this.controlPathId;
          this.selectedControlPathPollutant.facilitySiteId = this.facilitySiteId;

          if (!this.edit) {
            this.controlPathService.createPollutant(this.selectedControlPathPollutant).subscribe(() => {
              this.activeModal.close();
            });
          } else {
            this.controlPathService.updatePollutant(this.selectedControlPathPollutant).subscribe(() => {
              this.activeModal.close();
            });
          }
        }
      }
    }
    this.duplicateCheck = true;
  }


    searchPollutants = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => this.pollutantValues && this.pollutantValues.filter(v => v.pollutantName.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || v.pollutantCode.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || (v.pollutantCasId ? v.pollutantCasId.toLowerCase().indexOf(term.toLowerCase()) > -1 : false))
                                        .slice(0, 20)))


    pollutantFormatter = (result: Pollutant) => `${result.pollutantName}  -  ${result.pollutantCode} ${result.pollutantCasId ? ' - ' + result.pollutantCasId : ''}`;


}

