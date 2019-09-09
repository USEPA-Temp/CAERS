import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-busy-modal',
  templateUrl: './busy-modal.component.html',
  styleUrls: ['./busy-modal.component.scss']
})
export class BusyModalComponent implements OnInit {
    @Input() message: string;

  constructor() {  }

  ngOnInit() {
  }

}
