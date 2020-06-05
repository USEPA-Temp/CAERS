import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {
   EisData,
   EisDataReport,
   EisDataStats,
   EisSearchCriteria,
   EisSubmissionStatus
} from "src/app/shared/models/eis-data";
import {Observable} from "rxjs";

@Injectable({
   providedIn: 'root'
})
export class EisDataService {

   private baseUrl = 'api/eis/emissionsReport';  // URL to web api

   constructor(private http: HttpClient) {
   }

   retrieveStats(): Observable<EisDataStats> {

      return this.http.get<EisDataStats>(`${this.baseUrl}/stats`);
   }

   searchData(criteria: EisSearchCriteria): Observable<EisData> {

      let status = "";
      if (criteria.status && criteria.status !== EisSubmissionStatus.All) {

         for (const key in EisSubmissionStatus) {
            if (criteria.status === EisSubmissionStatus[key]) {
               status = key;
               break;
            }
         }
      }

      let params = new HttpParams()
         .append("year", criteria.year.toString())
         .append("status", status);

      return this.http.get<EisData>(this.baseUrl, {params: params});
   }

   updateComment(id: number, comment: string) : Observable<EisDataReport> {

      return this.http.put<EisDataReport>(`${this.baseUrl}/${id}/comment`, {value: comment});
   }
}
