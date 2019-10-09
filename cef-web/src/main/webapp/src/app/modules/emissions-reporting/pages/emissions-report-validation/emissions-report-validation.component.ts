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

      this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {

          this.sharedService.emitChange(data.facilitySite);

          this.emissionsReportingService.validateReport(data.facilitySite.emissionsReport.id)
              .subscribe(validationResult => {

                  this.validationResult = validationResult;
                  this.validationComplete = true;

              });
      });
  }

}
