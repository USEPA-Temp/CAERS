import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppProperty } from 'src/app/shared/models/app-property';

@Injectable({
  providedIn: 'root'
})
export class AdminPropertyService {

  private baseUrl = 'api/admin/property';  // URL to web api

  constructor(private http: HttpClient) { }

  retrieve(name: string): Observable<AppProperty> {
    const url = `${this.baseUrl}/${name}`;
    return this.http.get<AppProperty>(url);
  }
  
  retrieveAll(): Observable<AppProperty[]> {
    return this.http.get<AppProperty[]>(this.baseUrl);
  }

  update(prop: AppProperty): Observable<AppProperty> {
    const url = `${this.baseUrl}/${prop.name}`;
    return this.http.put<AppProperty>(url, prop);
  }

  bulkUpdate(props: AppProperty[]): Observable<AppProperty[]> {
    const url = `${this.baseUrl}`;
    return this.http.post<AppProperty[]>(url, props);
  }
}
