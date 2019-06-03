import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Facility } from '../model/facility';
import { CefFacility } from '../model/cef-facility';
import { FacilitySite } from '../model/facility-site';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class FacilityService {

  private baseUrl = 'api/facility/cdx';  // URL to web api

  constructor(private http: HttpClient) { }

  /** GET facilities from the server */
  getFacility(programId: string): Observable<Facility> {
    const url = `${this.baseUrl}/${programId}`;
    return this.http.get<Facility>(url)
      .pipe(
        tap(_ => this.log('fetched facility')),
        catchError(this.handleError<Facility>('getFacility', ))
      );
  }

  /** GET facilities from the server */
  getFacilities(userId: number): Observable<Facility[]> {
    const url = `${this.baseUrl}/user/${userId}`;
    return this.http.get<Facility[]>(url)
      .pipe(
        tap(_ => this.log('fetched facilities')),
        catchError(this.handleError<Facility[]>('getFacilities', []))
      );
  }

  /** GET facilities for current user from the server */
  getMyFacilities(): Observable<Facility[]> {
    const url = `${this.baseUrl}/user/my`;
    return this.http.get<Facility[]>(url)
      .pipe(
        tap(_ => this.log('fetched facilities')),
        catchError(this.handleError<Facility[]>('getMyFacilities', []))
      );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a FacilityService message with the console */
  private log(message: string) {
    console.log(`FacilityService: ${message}`);
  }
}
