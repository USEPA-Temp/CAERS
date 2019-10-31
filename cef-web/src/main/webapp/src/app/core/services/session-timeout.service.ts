import { Injectable } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserService } from 'src/app/core/services/user.service';
import { TimeoutModalComponent } from 'src/app/shared/components/timeout-modal/timeout-modal.component';

@Injectable({
  providedIn: 'root'
})
export class SessionTimeoutService {
  timer = null;
  dialogTimer = null;
  showingDialog = false;
  keepAlivePinged = false;

  options = {
    warnAfter: 900000, // 15 minutes
    redirAfter: 1200000, // 20 minutes
    keepAliveInterval: 60000 // 1 minute
  };

  constructor(private modalService: NgbModal, private userService: UserService) { }

  keepAlive() {

    if (this.keepAlivePinged === false) {

      this.keepAlivePinged = true;

      this.userService.getCurrentUser();

      setTimeout(() => {

        this.keepAlivePinged = false;

      }, this.options.keepAliveInterval);
    }
  }

  showTimeoutDialog() {

    if (this.showingDialog === false) {

      this.showingDialog = true;

      clearTimeout(this.timer);

      clearTimeout(this.dialogTimer);

      const timeRemaining = (this.options.redirAfter - this.options.warnAfter);

      // Log user out if modal is not interacted with
      this.dialogTimer = setTimeout(() => {

        window.location.href = 'logout';

      }, timeRemaining);

      const modalRef = this.modalService.open(TimeoutModalComponent, { size: 'lg', backdrop: 'static' });
      modalRef.componentInstance.timeout = timeRemaining;
      // Start timer again if they confirm they are still here
      modalRef.result.then((value: string) => {

        clearTimeout(this.dialogTimer);
        this.startSessionTimer();
        this.showingDialog = false;

      }, () => {

        clearTimeout(this.dialogTimer);
        window.location.href = 'logout';

        this.showingDialog = false;
      });
    }
  }

  startSessionTimer() {

    // Clear session timer
    clearTimeout(this.timer);

    this.keepAlive();

    // Set session timer
    this.timer = setTimeout(() => {

      this.showTimeoutDialog();

    }, this.options.warnAfter);
  }

  stopSessionTimer() {

    clearTimeout(this.timer);
    clearTimeout(this.dialogTimer);
  }

  refreshSessionTimer() {
    if (this.showingDialog === false) {
      this.startSessionTimer();
    }
  }
}
