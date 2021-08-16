import { Process } from 'src/app/shared/models/process';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmissionsProcessService {

  private baseUrl = 'api/emissionsProcess';  // URL to web api

  constructor(private http: HttpClient) { }

  create(process: Process): Observable<Process> {
    const url = `${this.baseUrl}`;
    return this.http.post<Process>(url, process);
  }

  update(process: Process): Observable<Process> {
    const url = `${this.baseUrl}/${process.id}`;
    return this.http.put<Process>(url, process);
  }

  /** GET specified release point from the server */
  retrieve(id: number): Observable<Process> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Process>(url);
  }

  retrievePrevious(id: number): Observable<Process> {
    const url = `${this.baseUrl}/${id}/previous`;
    return this.http.get<Process>(url);
  }

  retrieveForReleasePoint(releasePointId: number): Observable<Process[]> {
    const url = `${this.baseUrl}/releasePoint/${releasePointId}`;
    return this.http.get<Process[]>(url);
  }

  /**
   * GET all of the emissions processes for a specified emissions unit
   */
  retrieveForEmissionsUnit(emissionsUnitId: number): Observable<Process[]> {
    const url = `${this.baseUrl}/emissionsUnit/${emissionsUnitId}`;
    return this.http.get<Process[]>(url);
  }

    /** Delete specified emissions process from the database */
  delete(id: number): Observable<{}> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url);
  }
}
