import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

@Injectable({
  providedIn: 'root'
})
export class MasterFacilityRecordService {

    private baseUrl = 'api/masterFacility';  // URL to web api

    constructor(private http: HttpClient) { }

    getRecord(recordId: string): Observable<MasterFacilityRecord> {
        const url = `${this.baseUrl}/${recordId}`;
        return this.http.get<MasterFacilityRecord>(url);
    }

    getProgramRecords(programCode: string): Observable<MasterFacilityRecord[]> {
        const url = `${this.baseUrl}/program/${programCode}`;
        return this.http.get<MasterFacilityRecord[]>(url);
    }

    search(criteria: MasterFacilityRecord): Observable<MasterFacilityRecord[]> {
        const url = `${this.baseUrl}/search`;
        return this.http.post<MasterFacilityRecord[]>(url, criteria);
    }

    getMyRecords(): Observable<MasterFacilityRecord[]> {
        const url = `${this.baseUrl}/my`;
        return this.http.get<MasterFacilityRecord[]>(url);
    }

    getProgramSystemCodes(): Observable<BaseCodeLookup[]> {
        const url = `${this.baseUrl}/programSystemCodes`;
        return this.http.get<BaseCodeLookup[]>(url);
    }
}
