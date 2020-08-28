import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { UserFeedback } from 'src/app/shared/models/user-feedback';
import { UserFeedbackStats } from 'src/app/shared/models/user-feedback-stats';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserFeedbackService {

  private baseUrl = 'api/userFeedback';  // URL to web api

  constructor(private http: HttpClient) { }

  create(userFeedback: UserFeedback): Observable<UserFeedback> {
    const url = `${this.baseUrl}`;
    return this.http.post<UserFeedback>(url, userFeedback);
  }

  retrieveAllByYearAndAgency(year: number, agency: string): Observable<UserFeedback[]> {
    const url = `${this.baseUrl}/byYearAndAgency`;
    const params = new HttpParams()
         .append('year', year.toString())
         .append('agency', agency);
    return this.http.get<UserFeedback[]>(url, {params});
  }

  retrieveAvailableAgencies(): Observable<string[]> {
    const url = `${this.baseUrl}/agencies`;
    return this.http.get<string[]>(url);
  }

  retrieveAvailableYears(): Observable<number[]> {
    const url = `${this.baseUrl}/years`;
    return this.http.get<number[]>(url);
  }

  retrieveStatsByYearAndAgency(year: number, agency: string): Observable<UserFeedbackStats> {
    const params = new HttpParams()
         .append('year', year.toString())
         .append('agency', agency);
    const url = `${this.baseUrl}/stats/byYearAndAgency`;
    return this.http.get<UserFeedbackStats>(url, {params});
  }

}
