import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EmissionsReport } from "src/app/shared/models/emissions-report";
import { SharedService } from "src/app/core/services/shared.service";
import { EmissionsReportContextService } from "src/app/core/services/emissions-report-context.service";

@Component({
  selector: 'app-facility-information',
  templateUrl: './facility-information.component.html',
  styleUrls: ['./facility-information.component.scss']
})
export class FacilityInformationComponent implements OnInit {
    
    facilitySite: FacilitySite;
   

  constructor(
    private facilitySiteService: FacilitySiteService,
    private route: ActivatedRoute, private  sharedService: SharedService, 
    private emissionsReportContextService:EmissionsReportContextService) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { emissionsReport: EmissionsReport }) => {
        this.facilitySite=data.emissionsReport.facilitySite;
        this.sharedService.emitChange(data.emissionsReport);
      });
  }
}
