import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import { HttpRequest, HttpHandler, HttpInterceptor } from '@angular/common/http';
import {Observable, throwError, EMPTY, of} from 'rxjs';
import {retryWhen, delay, concatMap} from 'rxjs/operators';

const DELAY_MS : number = 1000;
const MAX_RETRIES : number = 1;

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

    constructor(private router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<any> {
        return next.handle(request)
            .pipe(retryWhen(errors => errors.pipe(
                delay(DELAY_MS),
                concatMap((error, count) => {

                    // console.log("In HttpErrorInterceptor", error, count);

                    if (error.status === 401) {

                        // not authenticated
                        alert(error.error.message);

                        // logout user => send back to CDX
                        window.location.href = 'logout';

                        return EMPTY;
                    }

                    if (error.status === 403 || error.status === 410) {

                        // don't repeat the alert -
                        // some pages make multiple requests for children of the entity
                        // that causes the first 403/410
                        let url = `/facility?error=${error.status}`;

                        if (this.router.url !== url) {

                            // not authorized or not found
                            alert(error.error.message);

                            this.router.navigateByUrl(url);
                        }

                        return EMPTY;
                    }

                    if (count < MAX_RETRIES) {

                        return of(error);
                    }

                    // give up error out...
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
            )));
    }

}
