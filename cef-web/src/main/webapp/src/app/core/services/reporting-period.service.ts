import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { ReportingPeriodUpdateResult } from 'src/app/shared/models/reporting-period-update-result';

@Injectable({
  providedIn: 'root'
})
export class ReportingPeriodService {

  private baseUrl = 'api/reportingPeriod';  // URL to web api

  constructor(private http: HttpClient) { }

  create(period: ReportingPeriod): Observable<ReportingPeriod> {
    const url = `${this.baseUrl}`;
    return this.http.post<ReportingPeriod>(url, period);
  }

  update(period: ReportingPeriod): Observable<ReportingPeriodUpdateResult> {
    const url = `${this.baseUrl}/${period.id}`;
    return this.http.put<ReportingPeriodUpdateResult>(url, period);
  }

  /** GET specified Reporting Period from the server */
  retrieve(id: number): Observable<ReportingPeriod> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<ReportingPeriod>(url);
  }

  retrieveForEmissionsProcess(processId: number): Observable<ReportingPeriod[]> {
    const url = `${this.baseUrl}/process/${processId}`;
    return this.http.get<ReportingPeriod[]>(url);
  }
}
