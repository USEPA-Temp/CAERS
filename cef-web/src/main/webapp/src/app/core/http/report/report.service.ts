import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';


@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private baseUrl = 'api/report';  // URL to web api

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

  /**
   * GET navigation flow for specified report.
   * Is currently static JSON, but should be generated on the backend so less data needs to be sent.
   */
  getNavigation(reportId: number): Observable<SideNavItem[]> {
    const url = 'assets/json/sideTreeNav.json';
//    const url = `${this.baseUrl}/${reportId}/nav`;`
    return this.http.get<SideNavItem[]>(url).pipe(
      map((items: SideNavItem[]) => items.map(item => SideNavItem.fromJson(item))));
  }
}
