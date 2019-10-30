import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import { HttpEvent, HttpRequest, HttpHandler, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
    constructor(private router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<any> {
        return next.handle(request)
            .pipe(
                retry(1),
                catchError((error: HttpErrorResponse) => {

                    console.log("In HttpErrorInterceptor");

                    if (error.status === 401) {

                        // not authenticated
                        alert(error.error.message);

                        return throwError(error.error.message);
                    }

                    if (error.status === 403 || error.status === 410) {

                        // not authorized or not found
                        alert(error.error.message);

                        return throwError(error.error.message);
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
