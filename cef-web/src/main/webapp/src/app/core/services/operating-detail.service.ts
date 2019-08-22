import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OperatingDetail } from 'src/app/shared/models/operating-detail';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OperatingDetailService {

  private baseUrl = 'api/operatingDetail';  // URL to web api

  constructor(private http: HttpClient) { }

  update(detail: OperatingDetail): Observable<OperatingDetail> {
    const url = `${this.baseUrl}/${detail.id}`;
    return this.http.put<OperatingDetail>(url, detail);
  }

}
