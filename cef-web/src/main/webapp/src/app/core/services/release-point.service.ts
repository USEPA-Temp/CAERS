import { ReleasePoint } from 'src/app/shared/models/release-point';
import { ReleasePointApportionment } from 'src/app/shared/models/release-point-apportionment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReleasePointService {

  private baseUrl = 'api/releasePoint';  // URL to web api

  constructor(private http: HttpClient) { }

  create(releasePoint: ReleasePoint): Observable<ReleasePoint> {
    const url = `${this.baseUrl}`;
    return this.http.post<ReleasePoint>(url, releasePoint);
  }

  update(releasePoint: ReleasePoint): Observable<ReleasePoint> {
    const url = `${this.baseUrl}/${releasePoint.id}`;
    return this.http.put<ReleasePoint>(url, releasePoint);
  }

  /** GET specified release point from the server */
  retrieve(id: number): Observable<ReleasePoint> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<ReleasePoint>(url);
  }

  retrieveForFacility(facilitySiteId: number): Observable<ReleasePoint[]> {
    const url = `${this.baseUrl}/facility/${facilitySiteId}`;
    return this.http.get<ReleasePoint[]>(url);
  }

  /** Delete specified release point from the database */
  delete(id: number): Observable<{}> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url);
  }

  /** Delete specified release point apportionment from the database */
  deleteAppt(id: number): Observable<{}> {
    const url = `${this.baseUrl}/appt/${id}`;
    return this.http.delete(url);
  }

  /** Create release point apportionment */
  createAppt(releasePointAppt: ReleasePointApportionment): Observable<{}> {
    const url = `${this.baseUrl}/appt/`;
    return this.http.post<ReleasePointApportionment>(url, releasePointAppt);
  }

  /** Update release point apportionment */
  updateAppt(releasePointAppt: ReleasePointApportionment): Observable<ReleasePointApportionment> {
    const url = `${this.baseUrl}/appt/${releasePointAppt.id}`;
    return this.http.put<ReleasePointApportionment>(url, releasePointAppt);
  }
}
