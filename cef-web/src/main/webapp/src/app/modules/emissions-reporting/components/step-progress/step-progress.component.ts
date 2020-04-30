import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { Location } from '@angular/common';
import { SharedService } from 'src/app/core/services/shared.service';

@Component({
  selector: 'app-step-progress',
  templateUrl: './step-progress.component.html',
  styleUrls: ['./step-progress.component.scss']
})
export class StepProgressComponent implements OnInit {
  facilitySite: FacilitySite;
  hideStepBar: boolean = false;

  constructor(private route: ActivatedRoute,
              private location: Location,
              private router: Router,
              private sharedService: SharedService) { }

  ngOnInit() {
    this.route.data
    .subscribe(data => {

      this.facilitySite = data.facilitySite;
    });

    this.sharedService.hideBoolChangeEmitted$.subscribe((result) => {
      this.hideStepBar = result;
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
