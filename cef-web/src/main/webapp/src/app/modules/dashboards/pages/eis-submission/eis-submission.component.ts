import {Component, OnInit} from '@angular/core';
import {SortEvent} from "src/app/shared/directives/sortable.directive";
import {EisDataService} from "src/app/core/services/eis-data.service";
import {EisSubmissionStatus} from "src/app/shared/models/eis-data-stats";

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

   availableYears: number[];
   availableStatuses: EisSubmissionStatus[];

   dataCategories = DataCategoryType;
   submissionTypes = SubmissionType;

   constructor(private eisDataService: EisDataService) {
   }

   ngOnInit() {

      this.availableYears = [];
      this.availableStatuses = [];

      this.stats = {
         notStarted: 0,
         qaFacility: 0,
         prodFacility: 0,
         qaEmissions: 0,
         prodEmissions: 0,
         complete: 0
      };

      this.retrieveDataStats();
   }

   retrieveDataStats() {

      this.eisDataService.retrieveStats().subscribe(stats => {

         this.availableYears =
            stats.availableYears.sort((a, b) => { return a < b ? b : a; })

         stats.statuses.forEach(stat => {

            switch (stat.status) {

               case null:
                  this.stats.notStarted = stat.count;
                  break;
               case EisSubmissionStatus.QaEmissions:
                  this.stats.qaEmissions = stat.count;
                  break;
               case EisSubmissionStatus.QaFacility:
                  this.stats.qaFacility = stat.count;
                  break;
               case EisSubmissionStatus.ProdEmissions:
                  this.stats.prodEmissions = stat.count;
                  break;
               case EisSubmissionStatus.ProdFacility:
                  this.stats.prodFacility = stat.count;
                  break;
            }

            if (stat.status) {
               this.availableStatuses.push(stat.status);
            }
         });

         this.availableStatuses.sort();
         this.availableStatuses.unshift(EisSubmissionStatus.All);
      })
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
