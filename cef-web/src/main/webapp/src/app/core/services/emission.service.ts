import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Emission } from 'src/app/shared/models/emission';
import { Observable } from 'rxjs';

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

  update(emission: Emission): Observable<Emission> {
    const url = `${this.baseUrl}/${emission.id}`;
    return this.http.put<Emission>(url, emission);
  }
}
