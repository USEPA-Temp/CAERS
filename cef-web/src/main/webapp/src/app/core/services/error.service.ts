import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor() { }

  getClientMessage(error: Error): string {
      if (!navigator.onLine) {
          return 'No Internet Connection';
      }
      return error.message ? error.message : error.toString();
  }

  getClientStack(error: Error): string {
      return error.message;
  }

  getServerMessage(error: HttpErrorResponse): string {
      return error.message;
  }

  getServerStack(error: HttpErrorResponse): string {
      // TODO: handle stack trace
      return 'stack';
  }
}