import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AppProperty } from 'src/app/shared/models/app-property';

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

  retrieveAnnouncementEnabled(): Observable<boolean> {
    const url = `${this.baseUrl}/announcement/enabled`;
    return this.http.get<boolean>(url);
  }

  retrieveAnnouncementText(): Observable<AppProperty> {
    const url = `${this.baseUrl}/announcement/text`;
    return this.http.get<AppProperty>(url);
  }

  retrieveReportAttachmentMaxSize(): Observable<AppProperty> {
    const url = `${this.baseUrl}/attachments/maxSize`;
    return this.http.get<AppProperty>(url);
  }

  retrieveExcelExportEnabled(): Observable<boolean> {
    const url = `${this.baseUrl}/excelExport/enabled`;
    return this.http.get<boolean>(url);
  }

}
