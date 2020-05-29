import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReportAttachment } from 'src/app/shared/models/report-attachment';
import { SharedService } from './shared.service';

@Injectable({
  providedIn: 'root'
})
export class ReportAttachmentService {
  reportId: string;

  private baseUrl = 'api/reports/reportId/attachments';  // URL to web api

  constructor(private sharedService: SharedService,
              private http: HttpClient) {

    this.sharedService.reportIdChangeEmitted$.subscribe(reportId => {
      this.reportId = reportId.toString();
      this.baseUrl = 'api/reports/' + this.reportId + '/attachments';
    });
  }

  /** GET download specified report attachment */
  downloadAttachment(attachmentId: number, ): Observable<any> {
    const url = `${this.baseUrl}/${attachmentId}`;
    return this.http.get(url, { responseType: 'blob' });
  }

  /** POST upload report attachment */
  uploadAttachment( reportAttachment: ReportAttachment,
                    attachment: File): Observable<HttpEvent<ReportAttachment>> {

    const url = `${this.baseUrl}/uploadAttachment`;

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
