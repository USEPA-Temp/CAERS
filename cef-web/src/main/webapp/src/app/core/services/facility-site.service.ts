import {CefFacility} from 'src/app/shared/models/cef-facility';
import {FacilitySite} from 'src/app/shared/models/facility-site';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';


@Injectable({
    providedIn: 'root'
})
export class FacilitySiteService {

    private baseUrl = 'api/facilitySite';  // URL to web api

    constructor(private http: HttpClient) {
    }

    /** GET specified facility site from the server */
    retrieve(id: number): Observable<FacilitySite> {
        const url = `${this.baseUrl}/${id}`;
        return this.http.get<FacilitySite>(url);
    }

    /** GET facility site for facility and report */
    retrieveForReport(programId: string, reportId: number): Observable<FacilitySite> {
        const url = `${this.baseUrl}/report/${reportId}/facility/${programId}`;
        return this.http.get<FacilitySite>(url);
    }

    createFacilitySite(facilitySite: FacilitySite) : Observable<FacilitySite> {

        const url = `${this.baseUrl}/report/${facilitySite.emissionsReport.id}/facility`;
        return this.http.post<FacilitySite>(url, facilitySite);
    }
}
