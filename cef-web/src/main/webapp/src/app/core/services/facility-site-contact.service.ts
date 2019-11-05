import { HttpClient } from '@angular/common/http';
import { FacilitySiteContact } from 'src/app/shared/models/facility-site-contact';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacilitySiteContactService {

  private baseUrl = 'api/facilitySiteContact';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET specified facility site contact from the server */
  retrieve(id: number): Observable<FacilitySiteContact> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<FacilitySiteContact>(url);
  }

  retrieveForFacility(facilitySiteId: number): Observable<FacilitySiteContact[]> {
    const url = `${this.baseUrl}/facility/${facilitySiteId}`;
    return this.http.get<FacilitySiteContact[]>(url);
  }

  /** Delete specified contact from the database */
  delete(id: number): Observable<{}> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url);
  }
}
