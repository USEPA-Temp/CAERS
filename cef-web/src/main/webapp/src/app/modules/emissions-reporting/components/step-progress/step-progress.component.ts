import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FacilitySite } from 'src/app/shared/models/facility-site';

@Component({
  selector: 'app-step-progress',
  templateUrl: './step-progress.component.html',
  styleUrls: ['./step-progress.component.scss']
})
export class StepProgressComponent implements OnInit {
  facilitySite: FacilitySite;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data
    .subscribe(data => {

      this.facilitySite = data.facilitySite;
    });
  }

  isOneOf(baseValue: string, testValues: string[]): boolean {
    for (const value of testValues) {
      if (baseValue === value) {
        return true;
      }
    }
    return false;
  }

}
