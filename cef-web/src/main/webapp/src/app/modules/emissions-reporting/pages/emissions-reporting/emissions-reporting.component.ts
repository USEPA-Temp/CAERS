import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from "src/app/shared/models/user";
import { UserContextService } from "src/app/core/services/user-context.service";
import { ChangeDetectorRef } from "@angular/core";

@Component( {
    selector: 'app-emissions-reporting',
    templateUrl: './emissions-reporting.component.html',
    styleUrls: ['./emissions-reporting.component.scss']
} )
export class EmissionsReportingComponent implements OnInit {
    facility: CdxFacility;
    showProgressBar: boolean=false;

    constructor( private route: ActivatedRoute,
        private reportService: EmissionsReportingService, private userContext: UserContextService, private cdRef : ChangeDetectorRef ) {
    }

    ngOnInit() {
        this.route.data
            .subscribe(( data: { facility: CdxFacility } ) => {
                this.facility = data.facility;
            } );
    }
    ngAfterViewChecked() {
        if ( this.userContext.user.role == 'Certifier' || this.userContext.user.role == 'Preparer' ) {
            this.showProgressBar = true;
            this.cdRef.detectChanges();
        }
      }
    
}
