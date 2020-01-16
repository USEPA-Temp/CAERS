import {CefFacility} from 'src/app/shared/models/cef-facility';
import {FacilitySite} from 'src/app/shared/models/facility-site';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import { FacilityNaicsCode } from 'src/app/shared/models/facility-naics-code';


@Injectable({
    providedIn: 'root'
})
export class FacilitySiteService {

    private baseUrl = 'api/facilitySite';  // URL to web api

    constructor(private http: HttpClient) {
    }

    /** Create new facility site */
    create(facilitySite: FacilitySite): Observable<FacilitySite> {
        const url = `${this.baseUrl}`;
        return this.http.post<FacilitySite>(url, facilitySite);
    }

    /** GET specified facility site from the server */
    retrieve(id: number): Observable<FacilitySite> {
        const url = `${this.baseUrl}/${id}`;
        return this.http.get<FacilitySite>(url);
    }

    /** GET facility site by eis program ID and report ID */
    retrieveForReport(programId: string, reportId: number): Observable<FacilitySite> {
        const url = `${this.baseUrl}/report/${reportId}/facility/${programId}`;
        return this.http.get<FacilitySite>(url);
    }

    update(facilitySite: FacilitySite): Observable<FacilitySite> {
        const url = `${this.baseUrl}/${facilitySite.id}`;
        return this.http.put<FacilitySite>(url, facilitySite);
    }

    /** Create facility NAICS */
    createFacilityNaics(facilityNaics: FacilityNaicsCode): Observable<{}> {
        const url = `${this.baseUrl}/naics/`;
        return this.http.post<FacilityNaicsCode>(url, facilityNaics);
    }

    /** Update facility NAICS */
    updateFacilityNaics(facilityNaics: FacilityNaicsCode): Observable<FacilityNaicsCode> {
        const url = `${this.baseUrl}/naics/${facilityNaics.id}`;
        return this.http.put<FacilityNaicsCode>(url, facilityNaics);
    }

    /** DELETE facility NAICS from the database */
    deleteFacilityNaics(facilityNaicsId: number): Observable<{}> {
        const url = `${this.baseUrl}/naics/${facilityNaicsId}`;
        return this.http.delete(url);
    }
}
