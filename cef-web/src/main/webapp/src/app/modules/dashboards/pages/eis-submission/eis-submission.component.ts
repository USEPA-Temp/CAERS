import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {EisDataService} from "src/app/core/services/eis-data.service";
import {EisDataReport, EisSubmissionStatus} from "src/app/shared/models/eis-data";
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

interface Sortable {

   key: string;
   value: any;
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
   maxlength: number;
   report: EisDataReport;
}

const commentMaxLength: number = 2000;

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

   txtFilter = new FormControl();

   txtComment = new FormControl();

   dlgEditComment: DlgEditComment;

   stats: EisDataStats;

   tableData: EisDataReport[];

   selectedReports: number[];

   availableYears: number[];

   availableStatuses: {
      key: string;
      value: EisSubmissionStatus;
   }[];

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
      this.selectedReports = [];

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

      this.txtComment.valueChanges.subscribe(() => {

         let comment = this.txtComment.value;
         if (comment && comment.length > this.dlgEditComment.maxlength) {
            this.txtComment.setValue(comment.substring(0, this.dlgEditComment.maxlength),
               {emitEvent: false, emitViewToModelChange: false, emitModelToViewChange: true});
         }

      });

      this.retrieveDataStats();
   }

   retrieveDataStats() {

      this.eisDataService.retrieveStats().subscribe(stats => {

         this.availableYears = stats.availableYears;

         let currYear = new Date().getFullYear() - 1;
         if (stats.availableYears.indexOf(currYear) < 0) {
            this.availableYears.push(currYear);
         }

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
               this.availableStatuses.push({
                  key: EisSubmissionStatus[stat.status],
                  value: stat.status
               });
            }
         });


         this.availableStatuses.unshift({
            key: "Aaaaaaaaaaaaaaaall",
            value: EisSubmissionStatus.All
         });

         this.cboFilterYear.setValue(currYear,
            {emitEvent: false, emitModelToViewChange: true, emitViewToModelChange: false});

         this.cboFilterStatus.setValue(EisSubmissionStatus.All,
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

         this.onSort({column: "facilityName", direction: "asc"});
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

      this.dlgEditComment = {

         report: report,
         waiting: false,
         maxlength: commentMaxLength,
         modalRef: this.modalService.open(this.editCommentTemplate, {
            backdrop: 'static'
         })
      };

      this.txtComment.setValue(report.comments);
      this.txtComment.enable();
   }

   onSubmitClick() {


   }

   onCancelCommentClick() {

      this.dlgEditComment.modalRef.dismiss();

      this.dlgEditComment = {
         report: null,
         waiting: false,
         maxlength: commentMaxLength,
         modalRef: null
      };
   }

   onUpdateCommentClick() {

      this.dlgEditComment.waiting = true;
      this.txtComment.disable();

      let comment = this.txtComment.value;
      if (comment && comment.length > this.dlgEditComment.maxlength) {
         comment = comment.substring(0, this.dlgEditComment.maxlength);
      }

      this.eisDataService.updateComment(this.dlgEditComment.report.emissionsReportId, comment)
         .subscribe(eisDataReport => {

            this.dlgEditComment.report.comments = eisDataReport.comments;

            this.onCancelCommentClick();
         });
   }

   onClearFilterClick() {

      this.txtFilter.setValue(null);
   }

   onSelectChange($event : any, report: EisDataReport) {

      if ($event.target.checked) {

         if (this.selectedReports.indexOf(report.emissionsReportId) < 0) {

            this.selectedReports.push(report.emissionsReportId);
         }

      } else {

         let idx = this.selectedReports.indexOf(report.emissionsReportId);
         if (idx > -1) {

            this.selectedReports.splice(idx, 1);
         }
      }
   }
}


