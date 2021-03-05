import {EmissionsReport} from 'src/app/shared/models/emissions-report';
import {HttpClient, HttpEvent, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ValidationResult} from 'src/app/shared/models/validation-result';
import {FacilitySite} from 'src/app/shared/models/facility-site';
import {CdxFacility} from '../../shared/models/cdx-facility';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

@Injectable({
    providedIn: 'root'
})
export class EmissionsReportingService {

    private baseUrl = 'api/emissionsReport';  // URL to web api

    constructor(private http: HttpClient) {
    }

    /** GET reports for specified facility from the server */
    getFacilityReports(facilityId: number): Observable<EmissionsReport[]> {
        const url = `${this.baseUrl}/facility/${facilityId}`;
        return this.http.get<EmissionsReport[]>(url);
    }

    /** GET most recent report for specified facility from the server */
    getCurrentReport(facilityId: string): Observable<EmissionsReport> {
        const url = `${this.baseUrl}/facility/${facilityId}/current`;
        return this.http.get<EmissionsReport>(url);
    }

    /** Get report by report id */
    getReport(reportId: string): Observable<EmissionsReport> {
        const url = `${this.baseUrl}/${reportId}`;
        return this.http.get<EmissionsReport>(url);
    }

    acceptReports(reportIds: number[], comments: string): Observable<EmissionsReport[]> {
        const url = `${this.baseUrl}/accept`;
        return this.http.post<EmissionsReport[]>(url, {
            reportIds,
            comments
        });
    }

    rejectReports(reportIds: number[], comments: string, attachmentId: number): Observable<EmissionsReport[]> {
        const url = `${this.baseUrl}/reject`;
        return this.http.post<EmissionsReport[]>(url, {
            reportIds,
            comments,
            attachmentId
        });
    }

    resetReports(reportIds: number[]): Observable<EmissionsReport[]> {
        const url = `${this.baseUrl}/reset`;
        return this.http.post<EmissionsReport[]>(url, reportIds);
    }

    /** POST request to the server to create a report for the current year from most previous copy */
    createReportFromPreviousCopy(masterFacilityRecordId: number, reportYear: number): Observable<HttpResponse<EmissionsReport>> {
        const url = `${this.baseUrl}/facility/${masterFacilityRecordId}`;
        return this.http.post<EmissionsReport>(url, {
            year: reportYear,
            masterFacilityRecordId,
            source: 'previous'
        }, {
            observe: 'response'
        });
    }

    /** POST request to the server to create a report for the current year from scratch */
    createReportFromScratch(masterFacilityRecordId: number,
                            reportYear: number): Observable<HttpResponse<EmissionsReport>> {

        const url = `${this.baseUrl}/facility/${masterFacilityRecordId}`;
        return this.http.post<EmissionsReport>(url, {
            year: reportYear,
            masterFacilityRecordId,
            source: 'fromScratch'
        }, {
            observe: 'response'
        });
    }

    /** POST request to the server to create a report for the current year from uploaded workbook */
    createReportFromUpload(facility: MasterFacilityRecord,
                           reportYear: number,
                           workbook: File): Observable<HttpEvent<EmissionsReport>> {

        const url = `${this.baseUrl}/facility/${facility.id}`;

        const formData = new FormData();
        formData.append('workbook', workbook);
        formData.append('metadata', new Blob([JSON.stringify({
            year: reportYear,
            masterFacilityRecordId: facility.id,
            eisProgramId: facility.eisProgramId,
            stateFacilityId: facility.agencyFacilityId,
            programSystemCode: facility.programSystemCode.code,
            source: 'fromScratch'
        })], {
            type: 'application/json'
        }));

        return this.http.post<EmissionsReport>(url, formData, {
            observe: 'events',
            reportProgress: true
        });
    }

    /** POST request to the server to create a validation result for report */
    validateReport(reportId: number): Observable<ValidationResult> {

        const url = `${this.baseUrl}/validation`;
        return this.http.post<ValidationResult>(url, {
            id: reportId
        });
    }

    /** POST request to the server to bulk upload an emissions report */
    uploadReport(jsonFileData: string, fileName: string): Observable<EmissionsReport> {
        const url = `${this.baseUrl}/upload/${encodeURI(fileName)}`;
        return this.http.post<EmissionsReport>(url, jsonFileData);
    }

    /** GET download excel template for specified report */
    downloadExcelExport(reportId: number): Observable<Blob> {
        const url = `${this.baseUrl}/export/${reportId}/excel`;
        return this.http.get(url, { responseType: 'blob' });
    }

    /** DELETE specified emissions report from database */
    delete(reportId: number): Observable<{}> {
        const url = `${this.baseUrl}/${reportId}`;
        return this.http.delete(url);
    }

    /** Sets hasSubmitted flag on specified emissions report to true */
    updateHasSubmittedFeedback(reportId: string): Observable<{}> {
        const url = `${this.baseUrl}/${reportId}/feedbackSubmitted`;
        return this.http.put(url, null);
    }
}
