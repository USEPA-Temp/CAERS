import { Injectable } from '@angular/core';
import { Resolve } from "@angular/router";
import { ActivatedRouteSnapshot } from "@angular/router";
import { RouterStateSnapshot } from "@angular/router";
import { EmissionsReport } from "src/app/shared/models/emissions-report";
import { Observable, of, EMPTY } from "rxjs";
import { mergeMap } from "rxjs/operators";
import { take } from "rxjs/internal/operators/take";
import { Router } from "@angular/router";
import { EmissionsReportContextService } from "src/app/core/services/emissions-report-context.service";

@Injectable( {
    providedIn: 'root'
} )
export class EmissionsReportResolverService implements Resolve<EmissionsReport>{
    emissionsReport: EmissionsReport;

    constructor( private emissionsReportContextService: EmissionsReportContextService, private router: Router) { }

    resolve( route: ActivatedRouteSnapshot, state: RouterStateSnapshot ) : Observable<EmissionsReport> | Observable<never>{
        const facilityId = route.paramMap.get('facilityId');
        return this.emissionsReportContextService.getEmissionsReport(facilityId).pipe(
                take(1),
                mergeMap(emissionsReport => {
                  if (emissionsReport) {
                    return of(emissionsReport);
                  } else { // id not found
                    this.router.navigate(['/facility']);
                    return EMPTY;
                  }
                })
              );
    }
}
