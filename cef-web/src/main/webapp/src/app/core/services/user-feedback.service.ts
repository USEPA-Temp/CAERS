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

  retrieveAllByYearAndProgramSystemCode(year: number, programSystemCode: string): Observable<UserFeedback[]> {
    const url = `${this.baseUrl}/byYearAndProgramSystemCode`;
    const params = new HttpParams()
         .append('year', year.toString())
         .append('programSystemCode', programSystemCode);
    return this.http.get<UserFeedback[]>(url, {params});
  }

  retrieveAvailableProgramSystemCodes(): Observable<string[]> {
    const url = `${this.baseUrl}/programSystemCodes`;
    return this.http.get<string[]>(url);
  }

  retrieveAvailableYears(): Observable<number[]> {
    const url = `${this.baseUrl}/years`;
    return this.http.get<number[]>(url);
  }

  retrieveStatsByYearAndProgramSystemCode(year: number, programSystemCode: string): Observable<UserFeedbackStats> {
    const params = new HttpParams()
         .append('year', year.toString())
         .append('programSystemCode', programSystemCode);
    const url = `${this.baseUrl}/stats/byYearAndProgramSystemCode`;
    return this.http.get<UserFeedbackStats>(url, {params});
  }

}
