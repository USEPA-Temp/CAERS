import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReportSummary } from 'src/app/shared/models/report-summary';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private baseUrl = 'api/reportSummary';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET report summary data from server for specified year and facility */
  retrieve(year: number, facilitySiteId: number): Observable<ReportSummary[]> {
    const url = `${this.baseUrl}/emissionsSummary/year/${year}/facilitySiteId/${facilitySiteId}`;
    return this.http.get<ReportSummary[]>(url);
  }
}
