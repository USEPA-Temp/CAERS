import { User } from 'src/app/shared/models/user';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserToken } from 'src/app/shared/models/user-token';
import { HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'api/user';  // URL to web api

  constructor(private http: HttpClient) { }

    /** GET the current user from the server */
  getCurrentUser(): Observable<User> {
    const url = `${this.userUrl}/me`;
    return this.http.get<User>(url);
  }

  /** GET the current user new NAAS token from the server */
  getCurrentUserNaasToken(): Observable<UserToken> {
    const url = `${this.userUrl}/token`;
    return this.http.get<UserToken>(url);
  }

  /** Initiate Logout for a user */
  initLogout(): Observable<any> {
    const url = 'logout';
    return this.http.post(url,'', {responseType: 'text'});
  }

}
