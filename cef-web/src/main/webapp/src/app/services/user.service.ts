import { User } from '../model/user';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { UserToken } from "src/app/model/user-token";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'api/user';  // URL to web api

  constructor(private http: HttpClient) { }

    /** GET the current user from the server */
  getCurrentUser (): Observable<User> {
    const url = `${this.userUrl}/me`;
    return this.http.get<User>(url)
      .pipe(
        tap(_ => this.log('fetched user')),
        catchError(this.handleError<User>('getCurrentUser'))
      );
  }

  /** GET the current user from the server */
  getCurrentUserNaasToken (): Observable<UserToken> {
    const url = `${this.userUrl}/token`;
    return this.http.get<UserToken>(url)
      .pipe(
        tap(_ => this.log('fetched user token')),
        catchError(this.handleError<UserToken>('getCurrentUserNaasToken'))
      );
  }
  
  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a UserService message with the console */
  private log(message: string) {
    console.log(`UserService: ${message}`);
  }
}
