import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';


@Injectable({
    providedIn: 'root'
})
export class EmissionsReportingService {

    private baseUrl = 'api/emissionsReport';  // URL to web api

    constructor(private http: HttpClient) { }

    /** GET reports for specified facility from the server */
    getFacilityReports(facilityId: string): Observable<EmissionsReport[]> {
        const url = `${this.baseUrl}/facility/${facilityId}`;
        return this.http.get<EmissionsReport[]>(url);
    }

    /** GET most recent report for specified facility from the server */
    getCurrentReport(facilityId: string): Observable<EmissionsReport> {
        const url = `${this.baseUrl}/facility/${facilityId}/current`;
        return this.http.get<EmissionsReport>(url);
    }

    /** POST request to the server to create a report for the current year */
    createReport(eisFacilityId: string, reportYear: number): Observable<HttpResponse<EmissionsReport>> {
        const url = `${this.baseUrl}/facility/${eisFacilityId}`;
        return this.http.post<EmissionsReport>(url, {
            year: reportYear,
            eisProgramId: eisFacilityId,
            source: "previous"
        }, {
            observe: "response"
        });
    }

    /** POST request to the server to create a report for the current year from FRS data */
    createReportFromFrs(eisFacilityId: string, reportYear: number) : Observable<EmissionsReport>{

        const url = `${this.baseUrl}/facility/${eisFacilityId}`;
        return this.http.post<EmissionsReport>(url, {
            year: reportYear,
            eisProgramId: eisFacilityId,
            source: "frs"
        });
    }
}
