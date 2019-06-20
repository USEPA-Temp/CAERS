import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Control } from 'src/app/shared/models/control';

@Injectable({
  providedIn: 'root'
})
export class ControlService {

  private baseUrl = 'api/control';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET specified control from the server */
  retrieve(id: number): Observable<Control> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Control>(url);
  }

  retrieveForFacilitySite(facilitySiteId: number): Observable<Control[]> {
    const url = `${this.baseUrl}/facilitySite/${facilitySiteId}`;
    return this.http.get<Control[]>(url);
  }
}
