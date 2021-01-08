import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserFacilityAssociation } from 'src/app/shared/models/user-facility-association';
import { Observable } from 'rxjs';

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

    getAssociationsForFacility(facilityId: number): Observable<UserFacilityAssociation[]> {
        const url = `${this.baseUrl}/facility/${facilityId}`;
        return this.http.get<UserFacilityAssociation[]>(url);
    }
}
