import { FacilitySite } from '../model/facility-site';
import { EmissionUnit } from '../reports/model/emission-unit';
import { ReleasePoint } from '../reports/model/release-point';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacilitySiteService {

  private baseUrl = 'api/facilitySite';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET specified emission unit from the server */
  retrieve(id: number): Observable<FacilitySite> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<FacilitySite>(url);
  }

  retrieveEmissionUnits(id: number): Observable<EmissionUnit[]> {
    const url = `${this.baseUrl}/${id}/emissionsUnits`;
    return this.http.get<EmissionUnit[]>(url);
  }

  retrieveReleasePoints(id: number): Observable<ReleasePoint[]> {
    const url = `${this.baseUrl}/${id}/releasePoints`;
    return this.http.get<ReleasePoint[]>(url);
  }
}
