import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';

@Injectable({
  providedIn: 'root'
})
export class EmissionUnitService {

    private staticJsonUrl = 'assets/json/emissionUnits.json';  // URL to web api
    private baseUrl = 'api/emissionsUnit';  // URL to web api

  constructor(private http: HttpClient) { }

  // TODO: implement on backend
  /** GET emission units for the specified facility from the server */
  getEmissionUnitsForReport(reportId: number): Observable<EmissionUnit[]> {
    const url = this.staticJsonUrl;
  // const url = `${this.baseUrl}/report/${reportId}`;
    return this.http.get<EmissionUnit[]>(url);
  }

  /** GET specified emission unit from the server */
  retrieve(id: number): Observable<EmissionUnit> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<EmissionUnit>(url);
  }

  retrieveForFacility(facilitySiteId: number): Observable<EmissionUnit[]> {
    const url = `${this.baseUrl}/facility/${facilitySiteId}`;
    return this.http.get<EmissionUnit[]>(url);
  }

  /**
   * Not currently used, but generates a nav flow from data.
   * This functionality should be moved to the backend, but I am leaving in place for reference.
   */
  retrieveReportNavTree(reportId: number): Observable<SideNavItem[]> {
    return this.getEmissionUnitsForReport(reportId).pipe(
      map((units: EmissionUnit[]) => units.map(unit => SideNavItem.fromEmissionUnit(unit)))
    );
  }
}
