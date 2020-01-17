import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { ValidationResult, ValidationItem } from 'src/app/shared/models/validation-result';
import { Observable } from 'rxjs';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-validation-reminder',
  templateUrl: './validation-reminder.component.html',
  styleUrls: ['./validation-reminder.component.scss']
})
export class ValidationReminderComponent implements OnInit {

  validationResult: ValidationResult;
  pageErrors: ValidationItem[] = [];
  pageWarnings: ValidationItem[] = [];

  navEnd: Observable<NavigationEnd>;
  currentUrl: string;

  constructor(private router: Router, private sharedService: SharedService) {
    this.navEnd = router.events.pipe(
      filter(evt => evt instanceof NavigationEnd)
    ) as Observable<NavigationEnd>;

    sharedService.validationResultChangeEmitted$
    .subscribe(result => {
      this.validationResult = result;
      this.generatePageValidations(this.validationResult, this.currentUrl);
    });
  }

  ngOnInit() {
    this.navEnd.subscribe(evt => {
      this.currentUrl = evt.url;
      this.generatePageValidations(this.validationResult, this.currentUrl);
    });
  }

  onCloseErrors() {
    this.validationResult.federalErrors = this.validationResult.federalErrors.filter(item => item.url !== this.currentUrl);
    this.validationResult.stateErrors = this.validationResult.stateErrors.filter(item => item.url !== this.currentUrl);
    this.generatePageValidations(this.validationResult, this.currentUrl);
  }

  onCloseWarnings() {
    this.validationResult.federalWarnings = this.validationResult.federalWarnings.filter(item => item.url !== this.currentUrl);
    this.validationResult.stateWarnings = this.validationResult.stateWarnings.filter(item => item.url !== this.currentUrl);
    this.generatePageValidations(this.validationResult, this.currentUrl);
  }

  private generatePageValidations(result: ValidationResult, url: string ): void {
    let errors: ValidationItem[] = [];
    let warnings: ValidationItem[] = [];

    if (url && result) {
      errors = errors.concat(result.federalErrors.filter(item => {
        return item.url === url;
      }));
      errors = errors.concat(result.stateErrors.filter(item => {
        return item.url === url;
      }));

      warnings = warnings.concat(result.federalWarnings.filter(item => {
        return item.url === url;
      }));
      warnings = warnings.concat(result.stateWarnings.filter(item => {
        return item.url === url;
      }));
    }

    this.pageErrors = errors;
    this.pageWarnings = warnings;

  }

}
