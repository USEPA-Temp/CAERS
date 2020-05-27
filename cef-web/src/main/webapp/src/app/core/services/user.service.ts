import { User } from 'src/app/shared/models/user';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserToken } from 'src/app/shared/models/user-token';
import { HttpHeaders } from "@angular/common/http";
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'api/user';  // URL to web api

  constructor(private http: HttpClient) { }

    /** GET the current user from the server */
  getCurrentUser(): Observable<User> {
    const url = `${this.userUrl}/me`;
    // retrieve user and then create a User class from it so functions work
    return this.http.get<User>(url).pipe(
      map(user => Object.assign(new User(), user))
    );
  }

  /** GET the current user new NAAS token from the server */
  getCurrentUserNaasToken(): Observable<UserToken> {
    const url = `${this.userUrl}/token`;
    return this.http.get<UserToken>(url);
  }

  /** Initiate CDX Handoff for a user */
  initHandoffToCdx(whereTo): Observable<any> {
    const url = `J2AHandoff?URL=${whereTo}`;
      return this.http.post(url,'', {responseType: 'text'});
  }

  /** Logout user from CAER app and redirect to next hop logout  */
  logoutUser() : void {

      this.http.post("logout", '', {responseType: 'text'})
          .subscribe((nextHop) => {

          window.location.href = nextHop;
      });
  }
}
