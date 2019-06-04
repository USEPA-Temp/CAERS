import { ReleasePoint } from '../model/release-point';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReleasePointService {

  private baseUrl = 'api/releasePoint';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET specified release point from the server */
  retrieve(id: number): Observable<ReleasePoint> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<ReleasePoint>(url);
  }

  retrieveForFacility(facilitySiteId: number): Observable<ReleasePoint[]> {
    const url = `${this.baseUrl}/facility/${facilitySiteId}`;
    return this.http.get<ReleasePoint[]>(url);
  }
}