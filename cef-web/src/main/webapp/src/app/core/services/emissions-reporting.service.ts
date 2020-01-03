import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {ValidationResult} from 'src/app/shared/models/validation-result';


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

    acceptReports(reportIds: number[], comments: String): Observable<EmissionsReport[]> {
        const url = `${this.baseUrl}/accept`;
        return this.http.post<EmissionsReport[]>(url, {reportIds: reportIds,
                                                       comments: comments });
    }

    rejectReports(reportIds: number[], comments: String): Observable<EmissionsReport[]> {
        const url = `${this.baseUrl}/reject`;
        return this.http.post<EmissionsReport[]>(url, {reportIds: reportIds,
                                                       comments: comments });
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

    /** POST request to the server to create a validation result for report */
    validateReport(reportId: number) : Observable<ValidationResult> {

        const url = `${this.baseUrl}/validation`;
        return this.http.post<ValidationResult>(url, {
            id: reportId
        });
    }

    /** POST request to the server to bulk upload an emissions report */
    uploadReport(jsonFileData: string): Observable<EmissionsReport> {
        const url = `${this.baseUrl}/upload`;
        return this.http.post<EmissionsReport>(url, jsonFileData);
    }

    /** DELETE specified emissions report from database */
    delete(reportId: number): Observable<{}> {
        const url = `${this.baseUrl}/${reportId}`;
        return this.http.delete(url);
    }
}
