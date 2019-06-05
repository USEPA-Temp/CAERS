import { Process } from '../model/process';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmissionsProcessService {

  private baseUrl = 'api/emissionsProcess';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET specified release point from the server */
  retrieve(id: number): Observable<Process> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Process>(url);
  }

  retrieveForReleasePoint(releasePointId: number): Observable<Process[]> {
    const url = `${this.baseUrl}/releasePoint/${releasePointId}`;
    return this.http.get<Process[]>(url);
  }
}
