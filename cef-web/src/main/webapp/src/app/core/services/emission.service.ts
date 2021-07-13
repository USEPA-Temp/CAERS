import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Emission } from 'src/app/shared/models/emission';
import { Observable } from 'rxjs';
import { BulkEntryEmissionHolder } from 'src/app/shared/models/bulk-entry-emission-holder';

@Injectable({
  providedIn: 'root'
})
export class EmissionService {

  private baseUrl = 'api/emission';  // URL to web api

  constructor(private http: HttpClient) { }

  create(emission: Emission): Observable<Emission> {
    const url = `${this.baseUrl}`;
    return this.http.post<Emission>(url, emission);
  }

  retrieve(id: number): Observable<Emission> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Emission>(url);
  }

  retrieveWithVariables(id: number): Observable<Emission> {
    const url = `${this.baseUrl}/${id}/variables`;
    return this.http.get<Emission>(url);
  }

  update(emission: Emission): Observable<Emission> {
    const url = `${this.baseUrl}/${emission.id}`;
    return this.http.put<Emission>(url, emission);
  }

    /** Delete specified emissions process from the database */
  delete(id: number): Observable<{}> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url);
  }

  calculateEmissionTotal(emission: Emission): Observable<Emission> {
    const url = `${this.baseUrl}/calculate`;
    return this.http.post<Emission>(url, emission);
  }

  retrieveForBulkEntry(facilitySiteId: number): Observable<BulkEntryEmissionHolder[]> {
    const url = `${this.baseUrl}/bulkEntry/${facilitySiteId}`;
    return this.http.get<BulkEntryEmissionHolder[]>(url);
  }

  bulkUpdate(facilitySiteId: number, emissions: Emission[]): Observable<BulkEntryEmissionHolder[]> {
    const url = `${this.baseUrl}/bulkEntry/${facilitySiteId}`;
    return this.http.put<BulkEntryEmissionHolder[]>(url, emissions);
  }

}
