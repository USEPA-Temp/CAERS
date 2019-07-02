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

    retrieveFacilitiesReportsUnderReview(): Observable<SubmissionUnderReview[]> {
        const url = `${this.baseUrl}/dashboard`;
        return this.http.get<SubmissionUnderReview[]>( url );
    }

}
