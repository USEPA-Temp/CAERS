import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserFeedback } from 'src/app/shared/models/user-feedback';
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

  retrieveByReportId(reportId: number): Observable<UserFeedback> {
    const url = `${this.baseUrl}/${reportId}`;
    return this.http.get<UserFeedback>(url);
  }
}
