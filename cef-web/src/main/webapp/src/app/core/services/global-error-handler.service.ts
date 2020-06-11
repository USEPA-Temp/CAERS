import { Injectable, ErrorHandler, Injector } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

import { LoggingService } from 'src/app/core/services/logging.service';
import { ErrorService } from 'src/app/core/services/error.service';
import { NotificationService } from 'src/app/core/services/notification.service';

@Injectable()
export class GlobalErrorHandlerService implements ErrorHandler {

    constructor(private injector: Injector) { }

    handleError(error: Error | HttpErrorResponse) {

        const router = this.injector.get(Router);
        const errorService = this.injector.get(ErrorService);
        const logger = this.injector.get(LoggingService);
        const notifier = this.injector.get(NotificationService);

        let message: string;
        let stackTrace: string;

        if (error instanceof HttpErrorResponse) {
            // Server Error
            message = `HTTP Error: ${errorService.getServerMessage(error)}`;
            stackTrace = `HTTP Error: ${errorService.getServerStack(error)}`;
            notifier.showError(message);
            logger.logError(message, stackTrace);
            router.navigateByUrl('/error');
        } else {
            // Client Error
            message = `Client Error: ${errorService.getClientMessage(error)}`;
            stackTrace = `Client Error: ${errorService.getClientStack(error)}`;
            notifier.showError(message);
            logger.logError(message, stackTrace);
            router.navigateByUrl('/error');
        }

        console.error(error);
    }
}
