import {Injectable} from '@angular/core';
import {HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler) {
        // I am not sure why we are setting this header, but it prevents the external SCC call from working correctly
        // so I am skipping this for that service
        if (!req.url.includes(environment.sccSearchApiUrl)) {
            const xhr = req.clone({
                headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
            });
            return next.handle(xhr);
        } else {
            return next.handle(req);
        }
    }
}
