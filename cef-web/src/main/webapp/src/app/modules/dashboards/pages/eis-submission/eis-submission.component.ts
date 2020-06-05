import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {EisDataService} from "src/app/core/services/eis-data.service";
import {EisData, EisDataReport, EisSubmissionStatus} from "src/app/shared/models/eis-data";
import {BaseSortableTable} from "src/app/shared/components/sortable-table/base-sortable-table";
import {FormControl} from "@angular/forms";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {NgbModalRef} from "@ng-bootstrap/ng-bootstrap/modal/modal-ref";

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

interface DlgEditComment {
   modalRef: NgbModalRef;
   waiting: boolean;
   report: EisDataReport;
}

@Component({
   selector: 'app-eis-submission',
   templateUrl: './eis-submission.component.html',
   styleUrls: ['./eis-submission.component.scss']
})
export class EisSubmissionComponent extends BaseSortableTable implements OnInit {

   @ViewChild('EditCommentModal', {static: true})
   editCommentTemplate: TemplateRef<any>;

   cboFilterStatus = new FormControl();
   cboFilterYear = new FormControl();

   txtComment = new FormControl();

   dlgEditComment: DlgEditComment;

   stats: EisDataStats;

   tableData: EisDataReport[];

   availableYears: number[];
   availableStatuses: EisSubmissionStatus[];

   dataCategories = DataCategoryType;
   submissionTypes = SubmissionType;

   constructor(private modalService: NgbModal,
               private eisDataService: EisDataService) {

      super();
   }

   ngOnInit() {

      this.availableYears = [];
      this.availableStatuses = [];

      this.tableData = [];

      this.stats = {
         notStarted: 0,
         qaFacility: 0,
         prodFacility: 0,
         qaEmissions: 0,
         prodEmissions: 0,
         complete: 0
      };

      this.cboFilterYear.valueChanges.subscribe(() => {
         this.retrieveData()
      });
      this.cboFilterStatus.valueChanges.subscribe(() => {
         this.retrieveData()
      });

      this.retrieveDataStats();
   }

   retrieveDataStats() {

      this.eisDataService.retrieveStats().subscribe(stats => {

         this.availableYears =
            stats.availableYears.sort((a, b) => {
               return a < b ? b : a;
            });

         this.availableYears.push(2018);

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

         if (this.availableYears.length === 0) {

            this.availableYears.push(new Date().getFullYear());
         }

         this.availableStatuses.sort();
         this.availableStatuses.unshift(EisSubmissionStatus.All);

         this.cboFilterYear.setValue(this.availableYears[0],
            {emitEvent: false, emitModelToViewChange: true, emitViewToModelChange: false});

         this.cboFilterStatus.setValue(this.availableStatuses[0],
            {emitEvent: false, emitModelToViewChange: true, emitViewToModelChange: false});

         this.retrieveData();
      });
   }

   retrieveData() {

      this.eisDataService.searchData({
         year: this.cboFilterYear.value,
         status: this.cboFilterStatus.value

      }).subscribe(resp => {

         this.tableData = resp.reports;
      });
   }

   onFilterQaFacility() {

      this.cboFilterStatus.setValue(EisSubmissionStatus.QaFacility);
   }

   onFilterNotStarted() {

      this.cboFilterStatus.setValue(EisSubmissionStatus.All);
   }

   onFilterProdEmissions() {

      this.cboFilterStatus.setValue(EisSubmissionStatus.ProdEmissions);
   }

   onFilterQaEmissions() {

      this.cboFilterStatus.setValue(EisSubmissionStatus.QaEmissions);
   }

   onFilterProdFacility() {

      this.cboFilterStatus.setValue(EisSubmissionStatus.ProdFacility);
   }

   onEditCommentClick(report: EisDataReport) {

      this.txtComment.setValue(report.comments);
      this.txtComment.enable();

      this.dlgEditComment = {

         report: report,
         waiting: false,
         modalRef: this.modalService.open(this.editCommentTemplate, {
            backdrop: 'static'
         })
      };
   }

   onCancelCommentClick() {

      this.dlgEditComment.modalRef.dismiss();

      this.dlgEditComment = {
         report: null,
         waiting: false,
         modalRef: null
      };
   }

   onUpdateCommentClick() {

      this.dlgEditComment.waiting = true;
      this.txtComment.disable();

      this.eisDataService.updateComment(this.dlgEditComment.report.emissionsReportId, this.txtComment.value)
         .subscribe(eisDataReport => {

            this.dlgEditComment.report.comments = eisDataReport.comments;

            this.onCancelCommentClick();
         });
   }
}
