/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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

    retrieveFacilitiesReportsUnderReviewByStatus(reportStatus: string): Observable<SubmissionUnderReview[]> {
        const url = `${this.baseUrl}/dashboard/byStatus/${reportStatus}`;
        return this.http.get<SubmissionUnderReview[]>( url );
    }

    retrieveFacilitiesReportsByYearAndStatus(reportYear: number, reportStatus: string): Observable<SubmissionUnderReview[]> {
        const url = `${this.baseUrl}/dashboard/byStatusAndYear/${reportYear}/${reportStatus}`;
        return this.http.get<SubmissionUnderReview[]>( url );
    }

    retrieveAllFacilitiesReportsForCurrentReportingYear(reportYear: number): Observable<SubmissionUnderReview[]> {
        const url = `${this.baseUrl}/dashboard/forCurrentReportingYear/${reportYear}`;
        return this.http.get<SubmissionUnderReview[]>( url );
    }
    }

