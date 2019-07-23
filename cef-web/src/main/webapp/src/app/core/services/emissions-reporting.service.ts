import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';


@Injectable({
  providedIn: 'root'
})
export class EmissionsReportingService {

  private baseUrl = 'api/emissionsReport';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET reports for specified facility from the server */
  getFacilityReports(facilityId: string): Observable<EmissionsReport[]> {
    const url = `${this.baseUrl}/facility/${facilityId}`;
    return this.http.get<EmissionsReport[]>(url);
  }

  /** GET most recent report for specified facility from the server */
  getCurrentReport(facilityId: string): Observable<EmissionsReport> {
    const url = `${this.baseUrl}/facility/${facilityId}/current`;
    return this.http.get<EmissionsReport>(url);
  }
}
