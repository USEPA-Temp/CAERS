import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Control } from 'src/app/shared/models/control';
import { EmissionsReportItem } from 'src/app/shared/models/emissions-report-item';

@Injectable({
  providedIn: 'root'
})
export class ControlService {

  private baseUrl = 'api/control';  // URL to web api

  constructor(private http: HttpClient) { }

  create(control: Control): Observable<Control> {
    const url = `${this.baseUrl}`;
    return this.http.post<Control>(url, control);
  }

  /** GET specified control from the server */
  retrieve(id: number): Observable<Control> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Control>(url);
  }

  retrieveForFacilitySite(facilitySiteId: number): Observable<Control[]> {
    const url = `${this.baseUrl}/facilitySite/${facilitySiteId}`;
    return this.http.get<Control[]>(url);
  }

  retrieveComponents(controlId: number): Observable<EmissionsReportItem[]> {
    const url = `${this.baseUrl}/components/${controlId}`;
    return this.http.get<EmissionsReportItem[]>(url);
  }

  update(control: Control): Observable<Control> {
    const url = `${this.baseUrl}/${control.id}`;
    return this.http.put<Control>(url, control);
  }

  delete(id: number): Observable<{}> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url);
  }

}
