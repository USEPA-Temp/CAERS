import { EmissionUnit } from '../model/emission-unit';
import { SideNavItem } from '../model/side-nav-item';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EmissionUnitService {

  private baseUrl = 'assets/json/emissionUnits.json';  // URL to web api

  constructor(private http: HttpClient) { }

  //TODO: implement on backend
  /** GET emission units for the specified facility from the server */
  getEmissionUnitsForReport (reportId: number): Observable<EmissionUnit[]> {
    const url = this.baseUrl;
//    const url = `${this.baseUrl}/report/${reportId}`;
    return this.http.get<EmissionUnit[]>(url);
  }

  //TODO: implement on backend
  /** GET specified emission unit from the server */
  retrieve (id: number): Observable<EmissionUnit> {
    const url = this.baseUrl;
//    const url = `${this.baseUrl}/${id}`;
    return this.http.get<EmissionUnit[]>(url).pipe(
      map((units: EmissionUnit[]) => units.find(unit => unit.id === id))
    );
  }

  /**
   * Not currently used, but generates a nav flow from data.
   * This functionality should be moved to the backend, but I am leaving in place for reference.
   */
  retrieveReportNavTree (reportId: number): Observable<SideNavItem[]> {
    return this.getEmissionUnitsForReport(reportId).pipe(
      map((units: EmissionUnit[]) => units.map(unit => SideNavItem.fromEmissionUnit(unit)))
    );
  }
}
