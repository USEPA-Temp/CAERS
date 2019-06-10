import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';
import { EmissionsUnitToSideNavPipe } from 'src/app/shared/pipes/emissions-unit-to-side-nav.pipe';


@Injectable({
  providedIn: 'root'
})
export class EmissionUnitService {

    private staticJsonUrl = 'assets/json/emissionUnits.json';  // URL to web api
    private baseUrl = 'api/emissionsUnit';  // URL to web api
    emissionsUnitToSideNavPipe: EmissionsUnitToSideNavPipe;

  constructor(private http: HttpClient) { 
      this.emissionsUnitToSideNavPipe = new EmissionsUnitToSideNavPipe();
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
   * Retrieves the report emissions units and transform them to side navigation tree items.
   */
  retrieveReportNavTree(facilityId: any): Observable<SideNavItem[]> {
    return this.retrieveForFacility(facilityId).pipe(
            map((items: EmissionUnit[]) => items.map(item => this.emissionsUnitToSideNavPipe.transform(item))));
  }
}
