import { EmissionsReport } from '../model/emissions-report';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private baseUrl = 'api/report';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET reports for specified facility from the server */
  getFacilityReports (facilityId: string): Observable<EmissionsReport[]> {
    const url = `${this.baseUrl}/facility/${facilityId}`;
    return this.http.get<EmissionsReport[]>(url);
  }
}
