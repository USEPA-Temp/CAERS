import { Injectable } from '@angular/core';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ControlAssignmentService {

  private baseUrl = 'api/controlAssignment';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET specified control assignment from the server */
  retrieve(id: number): Observable<ControlAssignment> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<ControlAssignment>(url);
  }

  retrieveForEmissionsProcess(processId: number): Observable<ControlAssignment[]> {
    const url = `${this.baseUrl}/process/${processId}`;
    return this.http.get<ControlAssignment[]>(url);
  }

  retrieveForEmissionsUnit(unitId: number): Observable<ControlAssignment[]> {
    const url = `${this.baseUrl}/unit/${unitId}`;
    return this.http.get<ControlAssignment[]>(url);
  }

  retrieveForReleasePoint(pointId: number): Observable<ControlAssignment[]> {
    const url = `${this.baseUrl}/releasePoint/${pointId}`;
    return this.http.get<ControlAssignment[]>(url);
  }
}
