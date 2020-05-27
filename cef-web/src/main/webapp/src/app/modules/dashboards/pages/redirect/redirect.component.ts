import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserContextService } from 'src/app/core/services/user-context.service';

@Component({
  selector: 'app-redirect',
  templateUrl: './redirect.component.html'
})
export class RedirectComponent {

  constructor(public userContext: UserContextService, public router: Router) {
    this.userContext.getUser()
    .subscribe(currentUser => {
        if (currentUser.canReview()) {
            this.router.navigateByUrl('/reviewer');
        } else {
            this.router.navigateByUrl('/facility');
        }
    });
  }

}
