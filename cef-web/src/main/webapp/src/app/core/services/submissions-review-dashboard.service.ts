import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SubmissionUnderReview } from "src/app/shared/models/submission-under-review";


@Injectable( {
    providedIn: 'root'
} )
export class SubmissionsReviewDashboardService {

    private baseUrl = 'api/submissionsReview';  // URL to web api

    constructor( private http: HttpClient ) {
    }

    retrieveFacilitiesReportsUnderReviewByStatus(reportStatus: string, agencyCode: string): Observable<SubmissionUnderReview[]> {
        const url = `${this.baseUrl}/dashboard/byStatus/${reportStatus}/${agencyCode}`;
        return this.http.get<SubmissionUnderReview[]>( url );
    }

    retrieveFacilitiesReportsByYearAndStatus(reportYear: number, reportStatus: string, agencyCode: string): Observable<SubmissionUnderReview[]> {
        const url = `${this.baseUrl}/dashboard/byStatusAndYear/${reportYear}/${reportStatus}/${agencyCode}`;
        return this.http.get<SubmissionUnderReview[]>( url );
    }

    retrieveAllFacilitiesReportsForCurrentReportingYear(reportYear: number, agencyCode: string): Observable<SubmissionUnderReview[]> {
        const url = `${this.baseUrl}/dashboard/forCurrentReportingYear/${reportYear}/${agencyCode}`;
        return this.http.get<SubmissionUnderReview[]>( url );
    }
    }

