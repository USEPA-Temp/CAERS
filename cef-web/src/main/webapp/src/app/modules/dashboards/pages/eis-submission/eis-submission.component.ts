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

const CommentMaxLength: number = 2000;

const CurrentYear = new Date().getFullYear() - 1;

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

   cboSubmitCategory = new FormControl();
   cboSubmitType = new FormControl();

   dlgEditComment: DlgEditComment;

   stats: EisDataStats;

   tableData: EisDataReport[];

   selectedReports: Set<number>;

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
      this.selectedReports = new Set<number>();

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

      this.availableYears.push(CurrentYear);
      this.cboFilterYear.setValue(CurrentYear,
         {emitEvent: false, emitModelToViewChange: true, emitViewToModelChange: false});

      this.availableStatuses.push(EisSubmissionStatus.All);

      this.cboFilterStatus.setValue(EisSubmissionStatus.All,
         {emitEvent: false, emitModelToViewChange: true, emitViewToModelChange: false});

      this.retrieveDataStats(() => {

         this.retrieveData();
      });
   }

   retrieveDataStats(onComplete?: () => void) {

      this.eisDataService.retrieveStats().subscribe({
         next: (stats) => {

            stats.availableYears.forEach(year => {
               if (this.availableYears.indexOf(year) < 0) {

                  this.availableYears.push(year)
               }
            });

            stats.statuses.forEach(stat => {

               let status: EisSubmissionStatus = EisSubmissionStatus[stat.status] as EisSubmissionStatus;

               switch (status) {

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

               if (status) {

                  let idx = this.availableStatuses.indexOf(status);

                  if (stat.count) {
                     if (idx < 0) {
                        this.availableStatuses.push(status);
                     }
                  } else {
                     if (idx > -1) {
                        this.availableStatuses.splice(idx, 1);
                     }
                  }
               }
            });

            return stats;
         },
         complete: () => {

            if (onComplete) {
               onComplete();
            }
         }
      });
   }

   retrieveData() {

      this.eisDataService.searchData({
         year: this.cboFilterYear.value,
         status: this.cboFilterStatus.value

      }).subscribe(resp => {

         this.tableData = resp.reports.map(report => {

            if (report.lastSubmissionStatus) {

               report.lastSubmissionStatus = EisSubmissionStatus[report.lastSubmissionStatus];
            }

            return report;
         });

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
         maxlength: CommentMaxLength,
         modalRef: this.modalService.open(this.editCommentTemplate, {
            backdrop: 'static'
         })
      };

      this.txtComment.setValue(report.comments);
      this.txtComment.enable();
   }

   onSubmitClick() {

      if (this.selectedReports.size) {

         let submissionStatus: EisSubmissionStatus = this.convertTypeAndCategory();

         if (submissionStatus) {

            this.eisDataService.submitReports({
               submissionStatus: submissionStatus,
               emissionsReportIds: this.selectedReports

            }).subscribe(data => {

               // grab new stats
               this.retrieveDataStats(() => {

                  this.retrieveData();
               });
            });
         }
      }
   }

   onCancelCommentClick() {

      this.dlgEditComment.modalRef.dismiss();

      this.dlgEditComment = {
         report: null,
         waiting: false,
         maxlength: CommentMaxLength,
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

   onSelectChange($event: any, report: EisDataReport) {

      if ($event.target.checked) {

         this.selectedReports.add(report.emissionsReportId);

      } else {

         this.selectedReports.delete(report.emissionsReportId);
      }
   }

   private convertTypeAndCategory(): EisSubmissionStatus {

      let result: EisSubmissionStatus = null;

      let type = this.cboSubmitType.value;
      let category = this.cboSubmitCategory.value;

      if (category === DataCategoryType.FACILITY_INVENTORY) {

         if (type === SubmissionType.QA) {

            result = EisSubmissionStatus.QaFacility;

         } else if (type === SubmissionType.PRODUCTION) {

            result = EisSubmissionStatus.ProdFacility;
         }

      } else if (category === DataCategoryType.POINT_EMISSIONS) {

         if (type === SubmissionType.QA) {

            result = EisSubmissionStatus.QaEmissions;

         } else if (type === SubmissionType.PRODUCTION) {

            result = EisSubmissionStatus.ProdEmissions;
         }
      }

      return result;
   }
}


