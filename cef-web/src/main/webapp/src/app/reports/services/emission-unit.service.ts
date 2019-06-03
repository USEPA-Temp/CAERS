import { EmissionUnit } from '../model/emission-unit';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SideNavItem } from 'src/app/model/side-nav-item';
import { EmissionsUnitToSideNavPipe } from "src/app/reports/pipes/emissions-unit-to-side-nav.pipe";

@Injectable({
  providedIn: 'root'
})
export class EmissionUnitService {

    private staticJsonUrl = 'assets/json/emissionUnits.json';  // URL to web api
    private baseUrl = 'api/emissionsUnit';  // URL to web api
    emissionsUnitToSideNavPipe: EmissionsUnitToSideNavPipe;
    
  constructor(private http: HttpClient) { 
      this.emissionsUnitToSideNavPipe=new EmissionsUnitToSideNavPipe();
  }

  retrieveForFacility(facilityId: number): Observable<EmissionUnit[]> {
    const url = `${this.baseUrl}/facility/${facilityId}`;
    return this.http.get<EmissionUnit[]>(url);
  }

  /** GET specified emission unit from the server */
  retrieve(id: number): Observable<EmissionUnit> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<EmissionUnit>(url);
  }

  /**
   * Retries the report emissions unit and transform them to side navigation tree items.
   */
  retrieveReportNavTree(facilityId: any): Observable<SideNavItem[]> {
    return this.retrieveForFacility(facilityId).pipe(
            map((items: EmissionUnit[]) => items.map(item => this.emissionsUnitToSideNavPipe.transform(item))));
  }
}
