import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor() { }

  showSuccess(message: string): void {
    console.log(`success notification: ${message}`);
  }

  showError(message: string): void {
      console.log(`error notification: ${message}`);
  }
}
