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
import { HttpClient, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Attachment } from "src/app/shared/models/attachment";
import { Communication } from "src/app/shared/models/communication";

@Injectable({
  providedIn: 'root'
})
export class CommunicationService {

	private baseUrl = 'api/communication';  // URL to web api
	
	  constructor(private http: HttpClient) {}

	/** POST upload communication attachment */
	uploadAttachment(metadata: Communication,
		attachment: File): Observable<HttpEvent<Attachment>> {

		const url = `${this.baseUrl}/uploadAttachment`;

		const formData = new FormData();
		formData.append('file', attachment);
		formData.append('metadata', new Blob([JSON.stringify({
			id: metadata.id,
			subject: metadata.subject,
			content: metadata.content
		})], {
			type: 'application/json'
		}));

		return this.http.post<Attachment>(url, formData, {
			observe: 'events',
			reportProgress: true
		});
	}
	
	/** PUT send SLT Notification email */
	sendSLTNotificationEmail(communication: Communication): Observable<Communication> {
        const url = `${this.baseUrl}/sendSLTNotification`;
        return this.http.put<Communication>(url, communication);
    }
}