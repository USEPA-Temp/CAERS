import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { Pollutant } from 'src/app/shared/models/pollutant';
import { ControlPollutant } from 'src/app/shared/models/control-pollutant';
import { LookupService } from 'src/app/core/services/lookup.service';
import { ControlService } from 'src/app/core/services/control.service';
import { Validators, FormBuilder, ValidatorFn, FormGroup } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-control-pollutant-modal',
  templateUrl: './control-pollutant-modal.component.html',
  styleUrls: ['./control-pollutant-modal.component.scss']
})
export class ControlPollutantModalComponent implements OnInit {
  pollutantValues: Pollutant[]; 
  selectedPollutant: Pollutant;
  selectedControlPollutant: ControlPollutant;
  controlId: number;
  facilitySiteId: number;
  edit: boolean;

  pollutantForm = this.fb.group({
      pollutant: [null , Validators.required],
      percentReduction: ['', [Validators.required,Validators.max(100)]]
  });

  constructor(private lookupService: LookupService,
              private fb: FormBuilder,
              public activeModal: NgbActiveModal,
              private controlService: ControlService) { }

  ngOnInit() {
    if(this.selectedControlPollutant){
      this.pollutantForm.reset(this.selectedControlPollutant);
    }
    else{
      this.selectedControlPollutant = new ControlPollutant();
    }
    this.lookupService.retrievePollutant()
    .subscribe(result => {
      this.pollutantValues = result;
    });
  }

  onClose() {
    this.activeModal.close('dontUpdate');
  }
  onSubmit() {
    this.selectedPollutant = this.pollutantForm.get('pollutant').value;
    this.selectedControlPollutant.pollutant = this.selectedPollutant;
    this.selectedControlPollutant.percentReduction = this.pollutantForm.get('percentReduction').value;
    this.selectedControlPollutant.controlId = this.controlId;
    this.selectedControlPollutant.facilitySiteId = this.facilitySiteId;

    if(!this.edit){
      this.controlService.createPollutant(this.selectedControlPollutant).subscribe(() =>{
        this.activeModal.close();
      });
    } else {
      this.controlService.updatePollutant(this.selectedControlPollutant).subscribe(()=>{
        this.activeModal.close();
      })
    }
  }


    searchPollutants = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => this.pollutantValues.filter(v => v.pollutantName.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || v.pollutantCode.toLowerCase().indexOf(term.toLowerCase()) > -1
                                        || (v.pollutantCasId ? v.pollutantCasId.toLowerCase().indexOf(term.toLowerCase()) > -1 : false))
                                        .slice(0, 20)))
    

    pollutantFormatter = (result: Pollutant) => `${result.pollutantName}  -  ${result.pollutantCode} ${result.pollutantCasId ? ' - ' + result.pollutantCasId : ''}`;
    

}
