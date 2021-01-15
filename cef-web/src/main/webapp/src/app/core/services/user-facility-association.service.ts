import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserFacilityAssociation } from 'src/app/shared/models/user-facility-association';
import { Observable } from 'rxjs';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

@Injectable({
  providedIn: 'root'
})
export class UserFacilityAssociationService {

    private baseUrl = 'api/userFacilityAssociation';  // URL to web api

    constructor(private http: HttpClient) { }

    getAssociation(id: number): Observable<UserFacilityAssociation> {
        const url = `${this.baseUrl}/${id}`;
        return this.http.get<UserFacilityAssociation>(url);
    }

    delete(id: number): Observable<{}> {
        const url = `${this.baseUrl}/${id}`;
        return this.http.delete(url);
    }

    createAssociationRequest(facility: MasterFacilityRecord) {
        const url = `${this.baseUrl}/request`;
        return this.http.post<UserFacilityAssociation>(url, facility);
    }

    getMyAssociations(): Observable<UserFacilityAssociation[]> {
        const url = `${this.baseUrl}/my`;
        return this.http.get<UserFacilityAssociation[]>(url);
    }

    getAssociationDetailsForFacility(facilityId: number): Observable<UserFacilityAssociation[]> {
        const url = `${this.baseUrl}/facility/${facilityId}/details`;
        return this.http.get<UserFacilityAssociation[]>(url);
    }
}
