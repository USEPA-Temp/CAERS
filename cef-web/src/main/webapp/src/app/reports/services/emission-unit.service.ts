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

  /** GET emission units for the specified facility from the server */
  getCurrentEmissionUnits (facilityId: string): Observable<EmissionUnit[]> {
    const url = `${this.baseUrl}`;
    return this.http.get<EmissionUnit[]>(url);
  }

  //TODO: implement real method
  retrieve (id: number): Observable<EmissionUnit> {
    const url = `${this.baseUrl}`;
    return this.http.get<EmissionUnit[]>(url).pipe(
      map((units: EmissionUnit[]) => units.find(unit => unit.id === id))
    );
  }

  retrieveFacilityNavTree (facilityId: string): Observable<SideNavItem[]> {
    return this.getCurrentEmissionUnits(facilityId).pipe(
      map((units: EmissionUnit[]) => units.map(unit => SideNavItem.fromEmissionUnit(unit)))
    );
  }
}
