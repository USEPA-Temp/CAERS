import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FormBuilder, Validators, ValidatorFn, FormGroup } from '@angular/forms';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Process } from 'src/app/shared/models/process';
import { FormUtilsService } from 'src/app/core/services/form-utils.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SccSearchModalComponent } from 'src/app/modules/emissions-reporting/components/scc-search-modal/scc-search-modal.component';
import { SccCode } from 'src/app/shared/models/scc-code';
import { AircraftEngineTypeCode } from 'src/app/shared/models/aircraft-engine-type-code';
import { PointSourceSccCode } from 'src/app/shared/models/point-source-scc-code';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';
import { FacilitySite } from 'src/app/shared/models/facility-site';

@Component({
  selector: 'app-edit-process-info-panel',
  templateUrl: './edit-process-info-panel.component.html',
  styleUrls: ['./edit-process-info-panel.component.scss']
})
export class EditProcessInfoPanelComponent implements OnInit, OnChanges {
  @Input() process: Process;
  @Input() unitIdentifier: string;
  aircraftSCCcheck = false;
  emissionsReportYear: number;

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
  });

  operatingStatusValues: BaseCodeLookup[];
  aircraftEngineTypeValue: AircraftEngineTypeCode[];
  aircraftEngineSCC: string[];
  pointSourceSccCode: PointSourceSccCode[];
  validSccCodeCheck: PointSourceSccCode[];

  constructor(
    private lookupService: LookupService,
    public formUtils: FormUtilsService,
    private modalService: NgbModal,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private toastr: ToastrService) { }

  ngOnInit() {

    this.lookupService.retrieveOperatingStatus()
    .subscribe(result => {
      this.operatingStatusValues = result;
    });

    this.lookupService.retrieveOperatingStatus()
    .subscribe(result => {
      this.operatingStatusValues = result;
    });

    this.lookupService.retrievePointSourceSccCodes()
    .subscribe(result => {
      this.pointSourceSccCode = result;
    });

    // SCC codes associated with Aircraft Engine Type Codes
    this.aircraftEngineSCC = [
      '2275001000', '2275020000', '2275050011', '2275050012', '2275060011', '2275060012'
    ];

    this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
      this.emissionsReportYear = data.facilitySite.emissionsReport.year;
      console.log(this.emissionsReportYear);
    });

    this.checkAircraftSCC();
  }

  ngOnChanges() {

    this.processForm.reset(this.process);
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

  checkPointSourceSccCode() {
    this.validSccCodeCheck = this.pointSourceSccCode.filter(value => (value.code === this.processForm.get('sccCode').value));

    if (this.validSccCodeCheck.length > 0) {
      for (let item of this.validSccCodeCheck) {
        if (item.lastInventoryYear !== null && (item.lastInventoryYear >= this.emissionsReportYear)) {
          this.toastr.warning('', item.code + ' has a retirement date of ' + item.lastInventoryYear + '. If applicable, you may want to add a more recent code.', {positionClass: 'toast-top-right'});
        } else if (item.lastInventoryYear !== null && (item.lastInventoryYear < this.emissionsReportYear)) {
          this.toastr.error('', item.code + ' was retired in ' + item.lastInventoryYear + '. Select an active SCC or an SCC with last inventory year greater than or equal to the current submission inventory year', {positionClass: 'toast-top-right'});
        }
      }
    } else if (this.validSccCodeCheck.length === 0) {
      this.toastr.error('', this.processForm.get('sccCode').value + ' is not a valid point source SCC. Please choose a valid code.', {positionClass: 'toast-top-right'});
    }

    this.checkAircraftSCC();
  }

  checkAircraftSCC() {
    const formSccCode = this.processForm.get('sccCode');
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

}
