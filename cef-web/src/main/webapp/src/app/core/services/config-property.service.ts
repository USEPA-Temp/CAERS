/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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

  retrieveAdminAnnouncementEnabled(): Observable<boolean> {
    const url = `${this.baseUrl}/announcement/enabled`;
    return this.http.get<boolean>(url);
  }

  retrieveAdminAnnouncementText(): Observable<AppProperty> {
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


  retrieveFacilityNaicsEntryEnabled(slt: string): Observable<boolean> {
    const url = `${this.baseUrl}/facilityNaics/${slt}/enabled`;
    return this.http.get<boolean>(url);
  }

  retrieveSltAnnouncementEnabled(slt: string): Observable<boolean> {
    const url = `${this.baseUrl}/announcement/${slt}/enabled`;
    return this.http.get<boolean>(url);
  }

  retrieveSltAnnouncementText(slt: string): Observable<AppProperty> {
    const url = `${this.baseUrl}/announcement/${slt}/text`;
    return this.http.get<AppProperty>(url);
  }

}
