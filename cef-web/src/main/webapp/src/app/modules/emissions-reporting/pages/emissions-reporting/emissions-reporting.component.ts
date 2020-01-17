import { CdxFacility } from 'src/app/shared/models/cdx-facility';
import { Component, OnInit, ChangeDetectorRef, AfterViewChecked } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component( {
    selector: 'app-emissions-reporting',
    templateUrl: './emissions-reporting.component.html',
    styleUrls: ['./emissions-reporting.component.scss']
} )
export class EmissionsReportingComponent implements OnInit, AfterViewChecked {
    facility: CdxFacility;

    constructor( private route: ActivatedRoute, private cdRef: ChangeDetectorRef) { }

    ngOnInit() {
        this.route.data
        .subscribe(( data: { facility: CdxFacility } ) => {
            this.facility = data.facility;
        });
    }

    // This might be bad coding practices allowing questionable code to work.
    // We should look at removing this and updating our code so it isn't needed at some point.
    ngAfterViewChecked() {
        this.cdRef.detectChanges();
    }

}
