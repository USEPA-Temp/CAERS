import { Component, OnInit, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-validation-message',
  templateUrl: './validation-message.component.html',
  styleUrls: ['./validation-message.component.scss']
})
export class ValidationMessageComponent implements OnInit {
  @Input() control: FormControl;
  @Input() disableRequired = false;

  constructor() { }

  ngOnInit() {
  }

}
