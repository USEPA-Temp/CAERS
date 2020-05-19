import { Component, OnInit } from '@angular/core';
import { FormBuilder} from '@angular/forms';
import { UserFeedbackService } from 'src/app/core/services/user-feedback.service';
import { UserFeedback } from 'src/app/shared/models/user-feedback';
import { ActivatedRoute, Router} from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SharedService } from 'src/app/core/services/shared.service';
import { FacilitySite } from 'src/app/shared/models/facility-site';

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
              private router: Router) { }

  ngOnInit() {
    this.route.paramMap
    .subscribe(params => {
      this.reportId = params.get('reportId');
      this.facilityId = params.get('facilityId');

      this.baseUrl = `/facility/${params.get('facilityId')}/report`;

      // If a user visits the feedback page, a feedback submission will automatically be created (if one doesnt already exist for the
      // report) to keep track of their page visit, the rest of the actual feedback values will remain null until actually submitted
      this.userFeedbackService.retrieveByReportId(this.reportId).subscribe((report) => {
        if (report === null) {
          const saveUserFeedback = new UserFeedback();
          saveUserFeedback.reportId = this.reportId;
          saveUserFeedback.hasSubmitted = false;
          saveUserFeedback.hasVisitedPage = true;
          this.feedbackForm.reset();
          Object.assign(saveUserFeedback, this.feedbackForm.value)
          this.userFeedbackService.create(saveUserFeedback).subscribe(() => {
          });
        }
      });
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
     this.userFeedbackService.retrieveByReportId(this.reportId).subscribe((report) => {
       if (report !== null) {
        const saveUserFeedback = new UserFeedback();
        saveUserFeedback.reportId = this.reportId;
        saveUserFeedback.hasSubmitted = true;
        saveUserFeedback.hasVisitedPage = true;
        saveUserFeedback.id = report.id;
        Object.assign(saveUserFeedback, this.feedbackForm.value);
        this.userFeedbackService.update(saveUserFeedback).subscribe(() => {
          this.toastr.success('', "Your feedback has successfully been submitted, thank you.");
          this.sharedService.emitHideBoolChange(false);
          this.router.navigateByUrl(this.baseUrl);
        });
       }
     });
  }

  onNoThanks() {
     this.userFeedbackService.retrieveByReportId(this.reportId).subscribe((report) => {
      if (report !== null) {
        const saveUserFeedback = new UserFeedback();
        saveUserFeedback.reportId = this.reportId;
        saveUserFeedback.hasSubmitted = true;
        saveUserFeedback.hasVisitedPage = true;
        saveUserFeedback.id = report.id;
        this.feedbackForm.reset();
        Object.assign(saveUserFeedback, this.feedbackForm.value)
        this.userFeedbackService.update(saveUserFeedback).subscribe(() => {
              this.sharedService.emitHideBoolChange(false);
              this.router.navigateByUrl(this.baseUrl);
        });
      }
    });
  }

}
