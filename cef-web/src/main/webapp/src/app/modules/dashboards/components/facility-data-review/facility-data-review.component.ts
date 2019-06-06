import { Component, OnInit, Input } from '@angular/core';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
    selector: 'app-facility-data-review',
    templateUrl: './facility-data-review.component.html',
    styleUrls: ['./facility-data-review.component.scss']
  })
export class FacilityDataReviewComponent implements OnInit {
  facility: CdxFacility;

  constructor(private route: ActivatedRoute, private location: Location) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { facility: CdxFacility }) => {
      console.log(data.facility);
      this.facility = data.facility;
      });
  }

  back() {
      this.location.back();
    }
}
