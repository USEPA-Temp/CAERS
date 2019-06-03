import { Component } from '@angular/core';
import { UserService } from 'src/app/core/http/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-redirect',
  templateUrl: './redirect.component.html'
})
export class RedirectComponent {

  constructor(public userService: UserService, public router: Router) {
    this.userService.getCurrentUser()
    .subscribe(currentUser => {
        if (currentUser.role === 'Reviewer') {
            this.router.navigateByUrl('/submissionReviewDashboard');
        } else {
            this.router.navigateByUrl('/facility');
        }
    });
  }

}
