import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReportAttachment } from 'src/app/shared/models/report-attachment';

@Injectable({
  providedIn: 'root'
})
export class ReportAttachmentService {

  private baseUrl = 'api/reportAttachments';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET report attachment from server for specified report and facility */
  retrieveAttachments(reportId: number, facilitySiteId: number): Observable<ReportAttachment[]> {
    const url = `${this.baseUrl}/report/${reportId}/facilitySiteId/${facilitySiteId}`;
    return this.http.get<ReportAttachment[]>(url);
  }

  /** GET download specified report attachment */
  downloadAttachment(facilitySiteId: number, attachmentId: number): Observable<any> {
    const url = `${this.baseUrl}/facilitySiteId/${facilitySiteId}/${attachmentId}`;
    return this.http.get(url, {responseType: 'blob'});
  }

  /** POST upload report attachment */
  uploadAttachment( facilitySiteId: number,
                    reportAttachment: ReportAttachment,
                    attachment: File): Observable<HttpEvent<ReportAttachment>> {

    const url = `${this.baseUrl}/facilitySiteId/${facilitySiteId}/uploadAttachment`;

    const formData = new FormData();
    formData.append('file', attachment);
    formData.append('metadata', new Blob([JSON.stringify({
      id: reportAttachment.id,
      reportId: reportAttachment.reportId,
      comments: reportAttachment.comments
    })], {
      type: 'application/json'
    }));

    return this.http.post<ReportAttachment>(url, formData, {
      observe: 'events',
      reportProgress: true
    });
  }

  /** Delete specified eport attachment from the database */
  deleteAttachment(attachmentId: number): Observable<{}> {
    const url = `${this.baseUrl}/${attachmentId}`;
    return this.http.delete(url);
  }

}
