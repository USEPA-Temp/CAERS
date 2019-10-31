import { Component, OnInit } from '@angular/core';
import {ValidationResult} from "src/app/shared/models/validation-result";
import {EmissionsReportingService} from "src/app/core/services/emissions-reporting.service";
import {ActivatedRoute} from "@angular/router";
import {FacilitySite} from "../../../../shared/models/facility-site";
import {SharedService} from "../../../../core/services/shared.service";

@Component({
  selector: 'app-emissions-report-validation',
  templateUrl: './emissions-report-validation.component.html',
  styleUrls: ['./emissions-report-validation.component.scss']
})
export class EmissionsReportValidationComponent implements OnInit {

    validationResult: ValidationResult;
    validationComplete: boolean;

  constructor(
      private route: ActivatedRoute,
      private sharedService: SharedService,
      private emissionsReportingService: EmissionsReportingService) {

      //
  }

  ngOnInit() {

      this.validationComplete = false;

      this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {
        
          this.emissionsReportingService.validateReport(data.facilitySite.emissionsReport.id)
              .subscribe(validationResult => {
                
                  this.validationResult = validationResult;
                  setTimeout(() => {
                    this.validationComplete = true;
                    if(validationResult['valid']){
                      if(this.hasWarnings()){
                        data.facilitySite.emissionsReport.validationStatus = "PASSED_WARNINGS";
                      }
                      else{
                        data.facilitySite.emissionsReport.validationStatus = "PASSED";
                      }
                     }
                    else{
                      data.facilitySite.emissionsReport.validationStatus = "FAILED";
                    }
                    this.sharedService.emitChange(data.facilitySite);
                  }, 5000);
              });
      });
  }

  hasErrors() {
      if (this.validationComplete === true && this.validationResult) {
        return this.validationResult.federalErrors.length || this.validationResult.stateErrors.length;
      }
      return false;
  }

  hasWarnings() {
      if (this.validationComplete === true && this.validationResult) {
        return this.validationResult.federalWarnings.length || this.validationResult.stateWarnings.length;
      }
      return false;
  }

}
