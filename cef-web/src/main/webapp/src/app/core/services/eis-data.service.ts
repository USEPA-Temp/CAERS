import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {
   EisData,
   EisDataReport,
   EisDataStats,
   EisReportStatusUpdate,
   EisSearchCriteria,
   EisSubmissionStatus
} from "src/app/shared/models/eis-data";
import {Observable} from "rxjs";
import { EisTranactionHistory } from 'src/app/shared/models/eis-tranaction-history';

@Injectable({
   providedIn: 'root'
})
export class EisDataService {

   private baseUrl = 'api/eis';  // URL to web api

   constructor(private http: HttpClient) {
   }

   retrieveStatsByYear(year: number): Observable<EisDataStats> {
      let params = new HttpParams()
         .append("year", year.toString());

      return this.http.get<EisDataStats>(`${this.baseUrl}/emissionsReport/stats`, {params: params});
   }

   retrieveTransactionHistory(): Observable<EisTranactionHistory[]> {

      return this.http.get<EisTranactionHistory[]>(`${this.baseUrl}/emissionsReport/history`);
   }

   searchData(criteria: EisSearchCriteria): Observable<EisData> {

      let status = this.convertStatusToEnum(criteria.status);

      let params = new HttpParams()
         .append("year", criteria.year.toString())
         .append("status", status);

      return this.http.get<EisData>(`${this.baseUrl}/emissionsReport`, {params: params});
   }

   submitReports(statusUpdate: EisReportStatusUpdate) : Observable<EisData>{

      return this.http.post<EisData>(`${this.baseUrl}/transaction`, {

         submissionStatus: this.convertStatusToEnum(statusUpdate.submissionStatus),
         emissionsReports: Array.from(statusUpdate.emissionsReportIds.values())
      });
   }

   updateComment(id: number, comment: string) : Observable<EisDataReport> {

      return this.http.put<EisDataReport>(`${this.baseUrl}/emissionsReport/${id}/comment`, {
         value: comment
      });
   }

   updateEisPassedStatus(id: number, passed: boolean): Observable<EisDataReport> {
      return this.http.put<EisDataReport>(`${this.baseUrl}/emissionsReport/${id}/passed`, {value: passed});
   }

   private convertStatusToEnum(status: EisSubmissionStatus) {

      let result = "";
      if (status && status !== EisSubmissionStatus.All) {

         for (const key in EisSubmissionStatus) {
            if (status === EisSubmissionStatus[key]) {
               result = key;
               break;
            }
         }
      }

      return result;
   }
}
