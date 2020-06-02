import { Component, OnInit } from '@angular/core';
import {SortEvent} from "src/app/shared/directives/sortable.directive";

interface EisDataStats {
  notStarted: number;
  qaFacility: number;
  prodFacility: number;
  qaEmissions: number;
  prodEmissions: number;
  complete: number;
}

enum SubmissionType {

  QA = "QA",
  PRODUCTION = "Production"
}

enum DataCategoryType {

  FACILITY_INVENTORY = "Facility Inventory",
  POINT_EMISSIONS = "Point Emissions"
}

@Component({
  selector: 'app-eis-submission',
  templateUrl: './eis-submission.component.html',
  styleUrls: ['./eis-submission.component.scss']
})
export class EisSubmissionComponent implements OnInit {

  stats: EisDataStats;

  dataCategories = DataCategoryType;
  submissionTypes = SubmissionType;

  constructor() { }

  ngOnInit() {

    this.stats = {
      notStarted: 0,
      qaFacility: 0,
      prodFacility: 0,
      qaEmissions: 0,
      prodEmissions: 0,
      complete: 0
    };
  }

  onSort($event: SortEvent) {

  }

  onFilterQaFacility() {

  }

  onFilterNotStarted() {

  }

  onFilterProdEmissions() {

  }

  onFilterQaEmissions() {

  }

   onFilterProdFacility() {

   }
}
