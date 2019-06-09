import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CdxFacility } from 'src/app/shared/models/cdx-facility';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class CdxFacilityService {

  private baseUrl = 'api/facility/cdx';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET facilities from the server */
  getFacility(programId: string): Observable<CdxFacility> {
    const url = `${this.baseUrl}/${programId}`;
    return this.http.get<CdxFacility>(url);
  }

  /** GET facilities from the server */
  getFacilities(userId: number): Observable<CdxFacility[]> {
    const url = `${this.baseUrl}/user/${userId}`;
    return this.http.get<CdxFacility[]>(url);
  }

  /** GET facilities for current user from the server */
  getMyFacilities(): Observable<CdxFacility[]> {
    const url = `${this.baseUrl}/user/my`;
    return this.http.get<CdxFacility[]>(url);
  }
}
