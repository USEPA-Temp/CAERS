import { Injectable } from '@angular/core';
import { HttpEvent, HttpRequest, HttpHandler, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request)
            .pipe(
                retry(1),
                catchError((error: HttpErrorResponse) => {
                    let errorMsg = '';
                    if (error.error instanceof ErrorEvent) {
                        // client side error
                        errorMsg = `Error: ${error.error.message}`;
                    } else {
                        // server side error
                        errorMsg = `Error Code: ${error.status}\nMessage: ${error.message}}`;
                    }
                    window.alert(errorMsg);
                    return throwError(errorMsg);
                })
            );
    }

}