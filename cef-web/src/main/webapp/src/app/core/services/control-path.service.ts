import { Injectable } from '@angular/core';
import { ControlPath } from 'src/app/shared/models/control-path';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ControlPathService {

  private baseUrl = 'api/controlPath';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET specified control path from the server */
  retrieve(id: number): Observable<ControlPath> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<ControlPath>(url);
  }

  retrieveForEmissionsProcess(processId: number): Observable<ControlPath[]> {
    const url = `${this.baseUrl}/process/${processId}`;
    return this.http.get<ControlPath[]>(url);
  }

  retrieveForEmissionsUnit(unitId: number): Observable<ControlPath[]> {
    const url = `${this.baseUrl}/unit/${unitId}`;
    return this.http.get<ControlPath[]>(url);
  }

  retrieveForReleasePoint(pointId: number): Observable<ControlPath[]> {
    const url = `${this.baseUrl}/releasePoint/${pointId}`;
    return this.http.get<ControlPath[]>(url);
  }
}
