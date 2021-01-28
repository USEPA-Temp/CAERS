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

    approveAssociations(associations: UserFacilityAssociation[]) {
        const url = `${this.baseUrl}/approve`;
        return this.http.post<UserFacilityAssociation[]>(url, associations);
    }

    rejectAssociations(associations: UserFacilityAssociation[], comments: string) {
        const url = `${this.baseUrl}/reject`;
        return this.http.post<UserFacilityAssociation[]>(url, {associations, comments});
    }

    getMyAssociations(): Observable<UserFacilityAssociation[]> {
        const url = `${this.baseUrl}/my`;
        return this.http.get<UserFacilityAssociation[]>(url);
    }

    getAssociationDetailsForFacility(facilityId: number): Observable<UserFacilityAssociation[]> {
        const url = `${this.baseUrl}/facility/${facilityId}/details`;
        return this.http.get<UserFacilityAssociation[]>(url);
    }

    getApprovedAssociationDetailsForFacility(facilityId: number): Observable<UserFacilityAssociation[]> {
        const url = `${this.baseUrl}/facility/${facilityId}/approved/details`;
        return this.http.get<UserFacilityAssociation[]>(url);
    }

    getPendingAssociationDetails(): Observable<UserFacilityAssociation[]> {
        const url = `${this.baseUrl}/pending/details`;
        return this.http.get<UserFacilityAssociation[]>(url);
    }

    migrateUserAssociations(): Observable<UserFacilityAssociation[]> {
        const url = `${this.baseUrl}/migrate`;
        return this.http.get<UserFacilityAssociation[]>(url);
    }
}
