/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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
    baseUrl: string;

  constructor(
      private route: ActivatedRoute,
      private router: Router,
      private sharedService: SharedService,
      private emissionsReportingService: EmissionsReportingService) {

  }

  ngOnInit() {

    this.route.paramMap
      .subscribe(map => {
        this.baseUrl = `/facility/${map.get('facilityId')}/report/${map.get('reportId')}`;
    });

      this.validationComplete = false;

      this.route.data.subscribe((data: { facilitySite: FacilitySite }) => {

          this.emissionsReportingService.validateReport(data.facilitySite.emissionsReport.id)
              .subscribe(validationResult => {

                  console.log(validationResult);
                  this.validationResult = validationResult;

                  this.validationComplete = true;
                  if (validationResult.valid) {
                    if (this.hasWarnings()) {
                      data.facilitySite.emissionsReport.validationStatus = ValidationStatus.PASSED_WARNINGS;
                    } else {
                      data.facilitySite.emissionsReport.validationStatus = ValidationStatus.PASSED;
                    }
                  } else {
                    data.facilitySite.emissionsReport.validationStatus = ValidationStatus.UNVALIDATED;
                  }
                  this.sharedService.emitChange(data.facilitySite);

                  this.validationResult.federalErrors.forEach(item => {
                    item.url = this.generateUrl(item.invalidValue);
                  });
                  this.validationResult.stateErrors.forEach(item => {
                    item.url = this.generateUrl(item.invalidValue);
                  });
                  this.validationResult.federalWarnings.forEach(item => {
                    item.url = this.generateUrl(item.invalidValue);
                  });
                  this.validationResult.stateWarnings.forEach(item => {
                    item.url = this.generateUrl(item.invalidValue);
                  });


                  this.sharedService.emitValidationResultChange(this.validationResult);
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

      } else if (EntityType.CONTROL_PATH === detail.type) {

        tree = this.router.createUrlTree(
          [BaseReportUrl.CONTROL_PATH, detail.id],
          {relativeTo: this.route.parent});

      } else if (EntityType.CONTROL === detail.type) {

        tree = this.router.createUrlTree(
          [BaseReportUrl.CONTROL_DEVICE, detail.id],
          {relativeTo: this.route.parent});

      } else if (EntityType.RELEASE_POINT === detail.type) {

        tree = this.router.createUrlTree(
          [BaseReportUrl.RELEASE_POINT, detail.id],
          {relativeTo: this.route.parent});

      } else if (EntityType.REPORT_ATTACHMENT === detail.type) {

        tree = this.router.createUrlTree(
          [BaseReportUrl.REPORT_SUMMARY],
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
