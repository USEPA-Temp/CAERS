import { Component, OnInit } from '@angular/core';
import {ValidationResult} from "src/app/shared/models/validation-result";
import {EmissionsReportingService} from "src/app/core/services/emissions-reporting.service";
import {ActivatedRoute, Router, UrlTree} from "@angular/router";
import {FacilitySite} from "../../../../shared/models/facility-site";
import {SharedService} from "../../../../core/services/shared.service";
import { ValidationStatus } from 'src/app/shared/enums/validation-status.enum';
import { ValidationDetail } from 'src/app/shared/models/validation-detail';
import { EntityType } from 'src/app/shared/enums/entity-type';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';


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
      private router: Router,
      private sharedService: SharedService,
      private emissionsReportingService: EmissionsReportingService) {

  }

  ngOnInit() {

      this.validationComplete = false;

      this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {

          this.emissionsReportingService.validateReport(data.facilitySite.emissionsReport.id)
              .subscribe(validationResult => {

                  console.log(validationResult);
                  this.validationResult = validationResult;
                  setTimeout(() => {
                    this.validationComplete = true;
                    if (validationResult['valid']) {
                      if (this.hasWarnings()) {
                        data.facilitySite.emissionsReport.validationStatus = ValidationStatus.PASSED_WARNINGS;
                      } else {
                        data.facilitySite.emissionsReport.validationStatus = ValidationStatus.PASSED;
                      }
                     } else {
                      data.facilitySite.emissionsReport.validationStatus = ValidationStatus.FAILED;
                    }
                    this.sharedService.emitChange(data.facilitySite);
                  }, 50);
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

  generateUrl(detail: ValidationDetail): string {

    let tree: UrlTree;
    if (detail) {
      if (EntityType.EMISSION === detail.type) {

        const period = detail.parents.find(p => p.type === EntityType.REPORTING_PERIOD);
        if (period) {
          tree = this.router.createUrlTree([
              BaseReportUrl.REPORTING_PERIOD,
              period.id,
              BaseReportUrl.EMISSION,
              detail.id
            ], {relativeTo: this.route.parent});
        }
      } else if (EntityType.EMISSIONS_PROCESS === detail.type) {

        tree = this.router.createUrlTree(
          [BaseReportUrl.EMISSIONS_PROCESS, detail.id],
          {relativeTo: this.route.parent});

      } else if (EntityType.EMISSIONS_REPORT === detail.type) {

        tree = this.router.createUrlTree(
          [BaseReportUrl.REPORT_SUMMARY],
          {relativeTo: this.route.parent});

      } else if (EntityType.EMISSIONS_UNIT === detail.type) {

        tree = this.router.createUrlTree(
          [BaseReportUrl.EMISSIONS_UNIT, detail.id],
          {relativeTo: this.route.parent});

      } else if (EntityType.FACILITY_SITE === detail.type) {

        tree = this.router.createUrlTree(
          [BaseReportUrl.FACILITY_INFO],
          {relativeTo: this.route.parent});


      } else if (EntityType.OPERATING_DETAIL === detail.type
        || EntityType.REPORTING_PERIOD === detail.type) {

        const process = detail.parents.find(p => p.type === EntityType.EMISSIONS_PROCESS);
        if (process) {
          tree = this.router.createUrlTree(
            [BaseReportUrl.EMISSIONS_PROCESS, process.id],
            {relativeTo: this.route.parent});
        }
      }
    }

    return tree ? this.router.serializeUrl(tree) : '.';
  }
}
