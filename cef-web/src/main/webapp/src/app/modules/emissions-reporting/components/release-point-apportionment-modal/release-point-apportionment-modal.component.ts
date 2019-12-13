import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Process } from 'src/app/shared/models/process';
import { ReleasePointApportionment } from 'src/app/shared/models/release-point-apportionment';
import { ReleasePointService } from 'src/app/core/services/release-point.service';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-release-point-apportionment-modal',
  templateUrl: './release-point-apportionment-modal.component.html',
  styleUrls: ['./release-point-apportionment-modal.component.scss']
})
export class ReleasePointApportionmentModalComponent implements OnInit {
  @Input() process: Process;
  @Input() releasePointApportionments: ReleasePointApportionment[];
  @Input() facilitySiteId: number;
  duplicateCheck: boolean = true;
  selectedReleasePoint: ReleasePointApportionment;
  releasePoints: ReleasePoint[];

  releasePointApptForm = this.fb.group({
    percent: ['', [Validators.required,Validators.max(100)]],
    selectedReleasePointAppt: ['',Validators.required],
  });

  constructor(public activeModal: NgbActiveModal,
              private fb: FormBuilder,
              private releasePointService: ReleasePointService,
              private toastr: ToastrService) { }

  ngOnInit() {
    this.selectedReleasePoint = new ReleasePointApportionment;
    this.releasePointService.retrieveForFacility(this.facilitySiteId)
    .subscribe(points => {  
      this.releasePoints = points;
    });
  }

  isValid() {
    return this.releasePointApptForm.valid;
  }

  onClose() {
    this.activeModal.dismiss();
  }

  onSubmit() {
    this.releasePointApportionments.forEach(apportionment => {
      if(apportionment.releasePointIdentifier === this.releasePointApptForm.get('selectedReleasePointAppt').value.toString()){
        this.duplicateCheck = false;
        this.toastr.error('',"This Emissions Process already contains this Release Point Apportionment, duplicates are not allowed.",{positionClass: 'toast-top-right'})
      }
    }) 
    if(this.duplicateCheck){
      this.releasePoints.forEach(releasePoint => {
        if(releasePoint.releasePointIdentifier === this.releasePointApptForm.get('selectedReleasePointAppt').value.toString()){
          this.selectedReleasePoint.releasePointId = releasePoint.id;
          this.selectedReleasePoint.releasePointIdentifier = releasePoint.releasePointIdentifier;
          this.selectedReleasePoint.releasePointDescription = releasePoint.description;
          this.selectedReleasePoint.releasePointTypeCode = releasePoint.typeCode;
          this.selectedReleasePoint.releasePoint = releasePoint;
          this.selectedReleasePoint.emissionsProcess = this.process;
          this.selectedReleasePoint.emissionsProcessId = this.process.id;
        }
      })

      if (!this.isValid()) {
        this.releasePointApptForm.markAllAsTouched();
      } else {

        Object.assign(this.selectedReleasePoint, this.releasePointApptForm.value);

        this.releasePointService.createAppt(this.selectedReleasePoint)
        .subscribe(() => {
          this.activeModal.close();
        });
      }
  }
  this.duplicateCheck = true;
  }

}