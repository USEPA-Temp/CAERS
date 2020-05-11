import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ConfigPropertyService {

  private baseUrl = 'api/property';  // URL to web api

  constructor(private http: HttpClient) { }

  retrieveBulkEntryEnabled(): Observable<boolean> {
    const url = `${this.baseUrl}/bulkEntry/enabled`;
    return this.http.get<boolean>(url);
  }

  retrieveUserFeedbackEnabled(): Observable<boolean> {
    const url = `${this.baseUrl}/userFeedback/enabled`;
    return this.http.get<boolean>(url);
  }
}
