import { Component, OnInit } from '@angular/core';
import { FormBuilder} from '@angular/forms';
import { UserFeedbackService } from 'src/app/core/services/user-feedback.service';
import { UserFeedback } from 'src/app/shared/models/user-feedback';
import { ActivatedRoute, Router} from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SharedService } from 'src/app/core/services/shared.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { EmissionsReport } from 'src/app/shared/models/emissions-report';

@Component({
  selector: 'app-user-feedback',
  templateUrl: './user-feedback.component.html',
  styleUrls: ['./user-feedback.component.scss']
})
export class UserFeedbackComponent implements OnInit {
  reportId: string;
  facilityId: string;
  facilitySite: FacilitySite;
  baseUrl: string;
  report: EmissionsReport;

  feedbackForm = this.fb.group({
    beneficialFunctionalityComments: [null],
    difficultFunctionalityComments: [null],
    enhancementComments: [null],
    intuitiveRating: [null],
    dataEntryScreens: [null],
    dataEntryBulkUpload: [null],
    calculationScreens: [null],
    controlsAndControlPathAssignments: [null],
    qualityAssuranceChecks: [null],
    overallReportingTime: [null]
  });

  constructor(private fb: FormBuilder,
              private userFeedbackService: UserFeedbackService,
              private route: ActivatedRoute,
              private toastr: ToastrService,
              private sharedService: SharedService,
              private router: Router,
              private emissionsReportingService: EmissionsReportingService) { }

  ngOnInit() {
    this.route.paramMap
    .subscribe(params => {
      this.reportId = params.get('reportId');
      this.facilityId = params.get('facilityId');
      this.emissionsReportingService.getCurrentReport(this.facilityId).subscribe((report) => {
        this.report = report;
      });
      this.baseUrl = `/facility/${params.get('facilityId')}/report`;
    });

    // emits the report info to the sidebar
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {
      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);
    });

    this.sharedService.emitHideBoolChange(true);
  }

  onSubmit()  {
        const saveUserFeedback = new UserFeedback();
        saveUserFeedback.reportId = this.reportId;
        Object.assign(saveUserFeedback, this.feedbackForm.value);
        this.userFeedbackService.create(saveUserFeedback).subscribe(() => {
          this.toastr.success('', "Your feedback has successfully been submitted, thank you.");
          this.emissionsReportingService.updateHasSubmittedFeedback(this.reportId, this.report).subscribe((result) => {
              this.sharedService.emitHideBoolChange(false);
              this.router.navigateByUrl(this.baseUrl);
          });

        });
  }

  onNoThanks() {
        this.emissionsReportingService.updateHasSubmittedFeedback(this.reportId, this.report).subscribe((result) => {
          this.sharedService.emitHideBoolChange(false);
          this.router.navigateByUrl(this.baseUrl);
        });
  }

}


