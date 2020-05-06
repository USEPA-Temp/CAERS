import { Component, OnInit } from '@angular/core';
import { FormBuilder} from '@angular/forms';
import { UserFeedbackService } from 'src/app/core/services/user-feedback.service';
import { UserFeedback } from 'src/app/shared/models/user-feedback';
import { ActivatedRoute, Router} from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-user-feedback',
  templateUrl: './user-feedback.component.html',
  styleUrls: ['./user-feedback.component.scss']
})
export class UserFeedbackComponent implements OnInit {
  reportId: string;
  facilityId: string;

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
      this.facilityId = params.get('facilityId')
    });
    this.sharedService.emitHideBoolChange(true);
    this.toastr.success('', "The Emission Report has been successfully electronically signed and submitted to the agency for review.");
  }

  onSubmit()  {
    const saveUserFeedback = new UserFeedback();
    saveUserFeedback.reportId = this.reportId;
    Object.assign(saveUserFeedback, this.feedbackForm.value);

    this.userFeedbackService.create(saveUserFeedback).subscribe(() => {
      const url = '/facility/'+this.facilityId+'/report';
      this.toastr.success('', "Your feedback has successfully been submitted, thank you.");
      this.router.navigateByUrl(url);
    });
  }

  onNoThanks() {
    this.sharedService.emitHideBoolChange(false);
    const url = '/facility/'+this.facilityId+'/report';
    this.router.navigateByUrl(url);
  }

}
