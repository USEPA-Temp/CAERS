import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { EmissionsReportingService } from 'src/app/core/services/emissions-reporting.service';
import { FacilitySiteService } from 'src/app/core/services/facility-site.service';
import { flatMap } from 'rxjs/operators';
import { map } from 'rxjs/internal/operators/map';
import { FacilitySite } from 'src/app/shared/models/facility-site';

@Injectable( {
    providedIn: 'root'
} )
export class EmissionsReportContextService {
    private emissionsReport$: Observable<EmissionsReport>;
    private programId: string;
    private report: EmissionsReport;

    constructor( private emissionsReportingService: EmissionsReportingService, private facilitySiteService:FacilitySiteService) {
    }

    getEmissionsReport( programId: string ): Observable<EmissionsReport> {
        if ( this.programId === programId ) {
            return this.emissionsReport$;
        } else {
            this.programId = programId;
            this.emissionsReport$=this.fetachEmissionsReport();
            return this.emissionsReport$;
        }
    }

    private fetachEmissionsReport() : Observable<EmissionsReport>{
        return this.emissionsReportingService.getCurrentReport(this.programId).pipe(
            flatMap((report:EmissionsReport) => {
                this.report=report;
                return this.facilitySiteService.retrieveForReport(this.programId, report.id);
            })).pipe(
                map( (site:FacilitySite) => {
                            this.report.facilitySite=site;
                            return this.report;
        }));
    }
}