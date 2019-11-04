import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-timeout-modal',
  templateUrl: './timeout-modal.component.html',
  styleUrls: ['./timeout-modal.component.scss']
})
export class TimeoutModalComponent implements OnInit {
  @Input() timeout: number;
  timeLeft: number;
  timeLeftText: string;
  timer = null;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
    this.timeLeft = Math.floor(this.timeout / 1000);

    this.startCountdownTimer();
  }

  onClose() {
    this.activeModal.dismiss();
  }

  onSubmit() {
    this.activeModal.close();
  }

  startCountdownTimer() {

    // Clear countdown timer
    clearTimeout(this.timer);

    // Set countdown message time value
    const secondsLeft = this.timeLeft >= 0 ? this.timeLeft : 0;

    const minLeft = Math.floor(secondsLeft / 60);
    const secRemain = secondsLeft % 60;
    let countTxt = minLeft > 0 ? minLeft + 'm' : '';
    if (countTxt.length > 0) {
      countTxt += ' ';
    }
    countTxt += secRemain.toFixed(0) + 's';

    this.timeLeftText = countTxt;

    // Countdown by one second
    this.timeLeft = this.timeLeft - 1;
    this.timer = setTimeout(() => {

      // Call self after one second
      this.startCountdownTimer();

    }, 1000);
  }

}
