import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import { HttpRequest, HttpHandler, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import {Observable, throwError, EMPTY} from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
    constructor(private router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<any> {
        return next.handle(request)
            .pipe(
                retry(1),
                catchError((error: HttpErrorResponse) => {

                    // console.log("In HttpErrorInterceptor");

                    if (error.status === 401) {

                        // not authenticated
                        alert(error.error.message);

                        // logout user => send back to CDX
                        window.location.href = 'logout';
                    }

                    if (error.status === 403 || error.status === 410) {

                        // not authorized or not found
                        alert(error.error.message);

                        this.router.navigateByUrl("/facility");

                        return EMPTY;
                    }

                    let errorMsg = '';
                    if (error.error instanceof ErrorEvent) {
                        // client side error
                        errorMsg = `Error: ${error.error.message}`;
                    } else {
                        // server side error
                        errorMsg = `Error Code: ${error.status}\nMessage: ${error.message}}`;
                    }

                    return throwError(errorMsg);
                })
            );
    }

}
