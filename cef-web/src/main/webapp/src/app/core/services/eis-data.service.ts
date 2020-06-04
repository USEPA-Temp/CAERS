import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {EisDataStats} from "src/app/shared/models/eis-data-stats";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EisDataService {

  private baseUrl = 'api/eis/emissionsReports';  // URL to web api

  constructor(private http: HttpClient) { }

  retrieveStats() : Observable<EisDataStats> {

    return this.http.get<EisDataStats>(`${this.baseUrl}/stats`);
  }
}
