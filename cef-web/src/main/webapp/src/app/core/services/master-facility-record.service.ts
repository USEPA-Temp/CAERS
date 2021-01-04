import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

@Injectable({
  providedIn: 'root'
})
export class MasterFacilityRecordService {

    private baseUrl = 'api/masterFacility';  // URL to web api

    constructor(private http: HttpClient) { }

    getProgramRecords(programCode: string): Observable<MasterFacilityRecord[]> {
        const url = `${this.baseUrl}/program/${programCode}`;
        return this.http.get<MasterFacilityRecord[]>(url);
    }

    getMyRecords(): Observable<MasterFacilityRecord[]> {
        const url = `${this.baseUrl}/my`;
        return this.http.get<MasterFacilityRecord[]>(url);
    }

    getRecord(recordId: string): Observable<MasterFacilityRecord> {
        const url = `${this.baseUrl}/${recordId}`;
        return this.http.get<MasterFacilityRecord>(url);
    }
}
